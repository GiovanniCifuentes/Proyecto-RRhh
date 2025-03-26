package vista;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 

import modelo.reporteNominaModelo;
import modelo.reporteNominaModelo.ReporteNomina;

public class reporteNominaVista extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private reporteNominaModelo modelo;
    private JButton btnGenerarPDF;
    private JButton btnGenerarExcel;

    public reporteNominaVista() {
        // Titulo de la ventana
        setTitle("Reporte de Nómina");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout
        setLayout(new BorderLayout());

        // Inicializar el modelo de datos
        modelo = new reporteNominaModelo();

        // Crear el panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Botones para generar los reportes
        btnGenerarPDF = new JButton("Generar Reporte PDF");
        btnGenerarExcel = new JButton("Generar Reporte Excel");

        panel.add(btnGenerarPDF);
        panel.add(btnGenerarExcel);

        // Crear la tabla para mostrar los datos
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Empleado");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("DPI");
        tableModel.addColumn("Fecha Ingreso");
        tableModel.addColumn("Salario Base");
        tableModel.addColumn("Estado");
        tableModel.addColumn("ID Nómina");
        tableModel.addColumn("Fecha Pago");
        tableModel.addColumn("Salario");
        tableModel.addColumn("Horas Extras");
        tableModel.addColumn("Comisiones");
        tableModel.addColumn("Bonificaciones");
        tableModel.addColumn("Valor Horas Extras");
        tableModel.addColumn("Total Devengado");
        tableModel.addColumn("ISR");
        tableModel.addColumn("Anticipos");
        tableModel.addColumn("Judiciales");
        tableModel.addColumn("Prestamos");
        tableModel.addColumn("IGSS");
        tableModel.addColumn("Deducciones");
        tableModel.addColumn("Total Pagar");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Agregar panel y tabla a la ventana
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar los datos en la tabla
        cargarDatosNomina();
    }

    // Método para cargar los datos en la tabla
    private void cargarDatosNomina() {
        try {
            List<ReporteNomina> reporteList = modelo.obtenerReporteNomina();
            for (ReporteNomina reporte : reporteList) {
                Object[] rowData = {
                        reporte.getIdEmpleado(),
                        reporte.getNombre(),
                        reporte.getApellido(),
                        reporte.getDPI(),
                        reporte.getFechaIngreso(),
                        reporte.getSalarioBaseFormateado(),  // Cambiado a getter formateado
                        reporte.getEstado(),
                        reporte.getIdNomina(),
                        reporte.getFechaPago(),
                        reporte.getSalarioFormateado(),      // Cambiado a getter formateado
                        reporte.getHorasExtras(),
                        reporte.getComisionesFormateado(),   // Cambiado a getter formateado
                        reporte.getBonificacionesFormateado(), // Cambiado a getter formateado
                        reporte.getValorHorasExtrasFormateado(), // Cambiado a getter formateado
                        reporte.getTotalDevengadoFormateado(), // Cambiado a getter formateado
                        reporte.getISRFormateado(),          // Cambiado a getter formateado
                        reporte.getAnticiposFormateado(),    // Cambiado a getter formateado
                        reporte.getJudicialesFormateado(),   // Cambiado a getter formateado
                        reporte.getPrestamosFormateado(),    // Cambiado a getter formateado
                        reporte.getIGSSFormateado(),         // Cambiado a getter formateado
                        reporte.getDeduccionesFormateado(),  // Cambiado a getter formateado
                        reporte.getTotalPagarFormateado()    // Cambiado a getter formateado
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al cargar los datos de nómina: " + e.getMessage());
        }
    }

    public void generarReportePDF(List<ReporteNomina> reporteList, String nombreUsuario) {
    try (PDDocument document = new PDDocument()) {
        // Configuración inicial
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        
        // Fuentes
        PDType1Font helveticaBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
        PDType1Font helvetica = new PDType1Font(Standard14Fonts.FontName.HELVETICA); 
        
        // Margenes y posiciones iniciales
        float marginLeft = 50;
        float marginRight = 50;
        float marginTop = 750;
        float currentY = marginTop;
        
        // --- ENCABEZADO PROFESIONAL ---
        contentStream.setFont(helveticaBold, 16);
        contentStream.beginText();
        contentStream.newLineAtOffset(marginLeft, currentY);
        contentStream.showText("EMPRESA S.A.");
        contentStream.endText();
        currentY -= 20;
        
        contentStream.setFont(helveticaBold, 14);
        contentStream.beginText();
        contentStream.newLineAtOffset(marginLeft, currentY);
        contentStream.showText("Reporte de Nómina");
        contentStream.endText();
        currentY -= 20;
        
        // Información de usuario y fecha
        contentStream.setFont(helvetica, 10);
        contentStream.beginText();
        contentStream.newLineAtOffset(marginLeft, currentY);
        contentStream.showText("Solicitado por: " + nombreUsuario);
        contentStream.endText();
        
        contentStream.beginText();
        contentStream.newLineAtOffset(400, currentY); // Alineado a la derecha
        contentStream.showText("Fecha: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        contentStream.endText();
        currentY -= 15;
        
        contentStream.beginText();
        contentStream.newLineAtOffset(marginLeft, currentY);
        contentStream.showText("Moneda: Quetzales (GTQ)");
        contentStream.endText();
        currentY -= 30; // Espacio adicional antes de los datos
        
        // Línea divisoria
        contentStream.moveTo(marginLeft, currentY);
        contentStream.lineTo(PDRectangle.A4.getWidth() - marginRight, currentY);
        contentStream.stroke();
        currentY -= 20;
        
        // --- CUERPO DEL REPORTE ---
        contentStream.setFont(helvetica, 12);
        
        for (ReporteNomina reporte : reporteList) {
            // Verificar espacio en página
            if (currentY < 100) { // Margen inferior
                contentStream.close();
                page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                currentY = marginTop;
                
                // Repetir encabezado en nuevas páginas
                contentStream.setFont(helveticaBold, 10);
                contentStream.beginText();
                contentStream.newLineAtOffset(marginLeft, currentY);
                contentStream.showText("EMPRESA S.A. - Continuación Reporte de Nómina");
                contentStream.endText();
                currentY -= 15;
                
                contentStream.beginText();
                contentStream.newLineAtOffset(marginLeft, currentY);
                contentStream.showText("Página " + (document.getNumberOfPages()));
                contentStream.endText();
                currentY -= 30;
                
                contentStream.setFont(helvetica, 12);
            }
            
            // Datos del empleado
            addTextWithWrap(contentStream, "Empleado: " + reporte.getNombre() + " " + reporte.getApellido(), 
                          marginLeft, currentY, PDRectangle.A4.getWidth() - marginRight - marginLeft);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "ID Empleado: " + reporte.getIdEmpleado(), marginLeft, currentY);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "DPI: " + reporte.getDPI(), marginLeft, currentY);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "Fecha Ingreso: " + reporte.getFechaIngreso().toString(), marginLeft, currentY);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "Estado: " + reporte.getEstado(), marginLeft, currentY);
            currentY -= 20;
            
            // Información de Nómina
            addTextWithWrap(contentStream, "Nómina: " + reporte.getIdNomina(), marginLeft, currentY);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "Fecha Pago: " + reporte.getFechaPago().toString(), marginLeft, currentY);
            currentY -= 20;
            
            // Detalles de compensación
            addTextWithWrap(contentStream, "Compensación:", marginLeft, currentY);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "  • Salario Base: " + reporte.getSalarioBaseFormateado(), marginLeft + 20, currentY);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "  • Horas Extras: " + reporte.getHorasExtras(), marginLeft + 20, currentY);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "  • Comisiones: " + reporte.getComisionesFormateado(), marginLeft + 20, currentY);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "  • Bonificaciones: " + reporte.getBonificacionesFormateado(), marginLeft + 20, currentY);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "Total Devengado: " + reporte.getTotalDevengadoFormateado(), marginLeft, currentY);
            currentY -= 20;
            
            // Deducciones
            addTextWithWrap(contentStream, "Deducciones:", marginLeft, currentY);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "  • ISR: " + reporte.getISRFormateado(), marginLeft + 20, currentY);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "  • Anticipos: " + reporte.getAnticiposFormateado(), marginLeft + 20, currentY);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "  • Judiciales: " + reporte.getJudicialesFormateado(), marginLeft + 20, currentY);
            currentY -= 15;
            
            addTextWithWrap(contentStream, "Total Deducciones: " + reporte.getDeduccionesFormateado(), marginLeft, currentY);
            currentY -= 20;
            
            // Total a pagar (destacado)
            contentStream.setFont(helveticaBold, 12);
            addTextWithWrap(contentStream, "TOTAL A PAGAR: " + reporte.getTotalPagarFormateado(), marginLeft, currentY);
            contentStream.setFont(helvetica, 12);
            currentY -= 30;
            
            // Línea divisoria entre empleados
            contentStream.moveTo(marginLeft, currentY);
            contentStream.lineTo(PDRectangle.A4.getWidth() - marginRight, currentY);
            contentStream.stroke();
            currentY -= 20;
        }
        
        contentStream.close();
        
        // Guardar el documento
        String filePath = "C://Users//HP//OneDrive//Documentos//7mo semestre//Analisis de Sistemas 2//Proyecto-RRhh//Reportes//ReporteNomina_" + 
                         LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".pdf";
        File file = new File(filePath);
        document.save(file);
        
        // Mostrar mensaje y abrir archivo
        JOptionPane.showMessageDialog(null, "Reporte PDF generado con éxito en: " + file.getAbsolutePath());
        
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

// Método auxiliar para manejar texto con wrap (opcional)
private void addTextWithWrap(PDPageContentStream contentStream, String text, float x, float y, float maxWidth) throws IOException {
    // Implementación básica de wrap de texto (puedes mejorarla)
    if (text.length() * 6 < maxWidth) { // Estimación aproximada
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    } else {
        // Dividir el texto en líneas más cortas
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        
        for (String word : words) {
            if (line.length() + word.length() + 1 < maxWidth / 6) {
                line.append(word).append(" ");
            } else {
                contentStream.showText(line.toString());
                contentStream.newLineAtOffset(0, -15); // Nueva línea
                line = new StringBuilder(word + " ");
            }
        }
        
        if (line.length() > 0) {
            contentStream.showText(line.toString());
        }
        
        contentStream.endText();
    }
}

private void addTextWithWrap(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
    contentStream.beginText();
    contentStream.newLineAtOffset(x, y);
    contentStream.showText(text);
    contentStream.endText();
}

public void generarReporteExcel(List<ReporteNomina> reporteList) {
    try {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte de Nómina");

        // Crear la fila de encabezado
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            headerRow.createCell(i).setCellValue(tableModel.getColumnName(i));
        }

        // Agregar los datos de la tabla
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                row.createCell(j).setCellValue(tableModel.getValueAt(i, j).toString());
            }
        }

        // Guardar el archivo Excel en la ruta especificada
        File file = new File("C://Users//HP//OneDrive//Documentos//7mo semestre//Analisis de Sistemas 2//Proyecto-RRhh//Reportes//ReporteNomina.xlsx");
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        }

        // Mostrar mensaje de éxito
        JOptionPane.showMessageDialog(this, "Reporte Excel generado con éxito en: " + file.getAbsolutePath());

        // Intentar abrir el archivo automáticamente
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo abrir el archivo automáticamente. Por favor, ábrelo manualmente.");
        }

        workbook.close();
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al generar o abrir el reporte Excel: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    // Getters para los botones
    public JButton getBtnGenerarPDF() {
        return btnGenerarPDF;
    }

    public JButton getBtnGenerarExcel() {
        return btnGenerarExcel;
    }

    // Método para mostrar errores
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}