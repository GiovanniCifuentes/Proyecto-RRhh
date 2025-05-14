package controlador;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import modelo.ModeloPrestaciones;
import vista.VistaPrestaciones;

public class ControladorPrestaciones {
    private final ModeloPrestaciones modelo;
    private final VistaPrestaciones vista;

    public ControladorPrestaciones(ModeloPrestaciones modelo, VistaPrestaciones vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.getBtnActualizar().addActionListener(e -> cargarDatos());
        vista.getBtnDescontarVacaciones().addActionListener(e -> descontarVacaciones());
        vista.getBtnGenerarPDF().addActionListener(e -> generarPDF());
        cargarDatos();
    }

    private void cargarDatos() {
        modelo.calcularPrestaciones();
        vista.actualizarTabla(modelo.obtenerPrestaciones());
        JOptionPane.showMessageDialog(null, "Prestaciones recalculadas y datos actualizados");
    }

    private void descontarVacaciones() {
        String idEmpleadoStr = JOptionPane.showInputDialog("Ingrese el ID del empleado:");
        String diasStr = JOptionPane.showInputDialog("Ingrese los días de vacaciones tomados:");

        if (idEmpleadoStr != null && diasStr != null) {
            try {
                int idEmpleado = Integer.parseInt(idEmpleadoStr);
                double diasTomados = Double.parseDouble(diasStr);
                modelo.descontarVacaciones(idEmpleado, diasTomados);
                cargarDatos();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese valores válidos.");
            }
        }
    }

    private void generarPDF() {
        int idEmpleado = vista.getIdEmpleadoSeleccionado();
        if (idEmpleado == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un empleado de la tabla primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Obtener datos del empleado y sus prestaciones
            List<String> datosEmpleado = obtenerDatosEmpleado(idEmpleado);
            List<String> datosPrestaciones = obtenerDatosPrestaciones(idEmpleado);

            // Generar el PDF
            generarPDF(datosEmpleado, datosPrestaciones);
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private List<String> obtenerDatosEmpleado(int idEmpleado) throws SQLException {
        List<String> datos = new ArrayList<>();
        try (Connection con = modelo.conectar();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT Nombre, Apellido, DPI, FechaIngreso, SalarioBase FROM Empleados WHERE IdEmpleado = ?")) {
            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                datos.add(rs.getString("Nombre"));
                datos.add(rs.getString("Apellido"));
                datos.add(rs.getString("DPI"));
                datos.add(rs.getDate("FechaIngreso").toString());
                datos.add(String.valueOf(rs.getDouble("SalarioBase")));
            }
        }
        return datos;
    }

    private List<String> obtenerDatosPrestaciones(int idEmpleado) throws SQLException {
        List<String> datos = new ArrayList<>();
        try (Connection con = modelo.conectar();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT DiasVacaciones, Aguinaldo, Bono14, FechaCalculo FROM Prestaciones WHERE IdEmpleado = ? AND CONVERT(DATE, FechaCalculo) = CONVERT(DATE, GETDATE())")) {
            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                datos.add(String.valueOf(rs.getDouble("DiasVacaciones")));
                datos.add(String.valueOf(rs.getDouble("Aguinaldo")));
                datos.add(String.valueOf(rs.getDouble("Bono14")));
                datos.add(rs.getTimestamp("FechaCalculo").toString());
            }
        }
        return datos;
    }

    private void generarPDF(List<String> datosEmpleado, List<String> datosPrestaciones) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                PDType1Font fontNormal = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
                
                float margin = 50;
                float yPosition = 750;
                float lineHeight = 20;

                // Encabezado del documento
                contentStream.setFont(fontBold, 16);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Reporte de Prestaciones");
                contentStream.endText();
                yPosition -= lineHeight * 1.5f;

                contentStream.setFont(fontNormal, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Fecha: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                contentStream.endText();
                yPosition -= lineHeight * 2;

                // Información del empleado
                contentStream.setFont(fontBold, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("1. Información del Empleado");
                contentStream.endText();
                yPosition -= lineHeight;

                contentStream.setFont(fontNormal, 12);
                String[] etiquetasEmpleado = {"Nombre:", "Apellido:", "DPI:", "Fecha de Ingreso:", "Salario Base:"};
                for (int i = 0; i < datosEmpleado.size(); i++) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText(etiquetasEmpleado[i] + " " + datosEmpleado.get(i));
                    contentStream.endText();
                    yPosition -= lineHeight;
                }

                yPosition -= lineHeight / 2;

                // Prestaciones del empleado
                contentStream.setFont(fontBold, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("2. Prestaciones al Día de Hoy");
                contentStream.endText();
                yPosition -= lineHeight;

                contentStream.setFont(fontNormal, 12);
                String[] etiquetasPrestaciones = {"Días de Vacaciones:", "Aguinaldo:", "Bono 14:", "Fecha de Cálculo:"};
                for (int i = 0; i < datosPrestaciones.size(); i++) {
                    String valor = datosPrestaciones.get(i);
                    // Formatear valores monetarios
                    if (i == 1 || i == 2) { // Aguinaldo y Bono14
                        BigDecimal valorDecimal = new BigDecimal(valor);
                        valorDecimal = valorDecimal.setScale(2, RoundingMode.HALF_UP);
                        valor = "Q" + valorDecimal.toString();
                    }
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText(etiquetasPrestaciones[i] + " " + valor);
                    contentStream.endText();
                    yPosition -= lineHeight;
                }
            }

            // Guardar el documento
            String nombreArchivo = "Prestaciones_" + datosEmpleado.get(1) + "_" + 
                                 LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".pdf";
            File file = new File(nombreArchivo);
            document.save(file);

            // Mostrar mensaje y abrir el PDF
            JOptionPane.showMessageDialog(null, "PDF generado con éxito: " + file.getAbsolutePath());
            
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        }
    }
}

