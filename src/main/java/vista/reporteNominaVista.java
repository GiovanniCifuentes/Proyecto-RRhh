package vista;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    // Método para generar el reporte PDF
        public void generarReportePDF(List<ReporteNomina> reporteList) {
            try (PDDocument document = new PDDocument()) {
                // Crear la primera página en orientación vertical (A4 estándar)
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);
    
                // Inicializar el PDPageContentStream
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
    
                // Configurar la fuente y el tamaño para el título (usando Helvetica-Bold)
                PDType1Font helveticaBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                contentStream.setFont(helveticaBold, 18);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750); // Posición del título
                contentStream.showText("Reporte de Nómina");
                contentStream.endText();
    
                // Configurar la fuente para los datos (usando Helvetica normal)
                PDType1Font helvetica = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
                contentStream.setFont(helvetica, 12);
                int startY = 710; // Posición inicial de los datos
    
                // Dibujar los datos en formato de lista
                for (ReporteNomina reporte : reporteList) {
                    // Mostrar información del empleado
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, startY);
                    contentStream.showText("Empleado: " + reporte.getNombre() + " " + reporte.getApellido());
                    contentStream.endText();
    
                    startY -= 15;
    
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, startY);
                    contentStream.showText("ID Empleado: " + reporte.getIdEmpleado());
                    contentStream.endText();
    
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, startY - 15);
                    contentStream.showText("DPI: " + reporte.getDPI());
                    contentStream.endText();
    
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, startY - 30);
                    contentStream.showText("Fecha Ingreso: " + reporte.getFechaIngreso().toString());
                    contentStream.endText();
    
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, startY - 45);
                    contentStream.showText("Estado: " + reporte.getEstado());
                    contentStream.endText();
    
                    // Información de Nómina
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, startY - 60);
                    contentStream.showText("Nómina: " + reporte.getIdNomina());
                    contentStream.endText();
    
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, startY - 75);
                    contentStream.showText("Fecha Pago: " + reporte.getFechaPago().toString());
                    contentStream.endText();
    
                    // Detalles de compensación
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, startY - 105);
                    contentStream.showText("Compensación: Salario Base: " + reporte.getSalarioBaseFormateado().toString() +
                            " | Horas Extras: " + String.valueOf(reporte.getHorasExtras()) +
                            " | Comisiones: " + reporte.getComisionesFormateado().toString()+
                            " | Bonoficaciones: " + reporte.getBonificacionesFormateado().toString());
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, startY - 90);
                    contentStream.showText("Total Devengado: " + reporte.getTotalDevengadoFormateado().toString());
                    contentStream.endText();
    
                    // Deducciones
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, startY - 120);
                    contentStream.showText("Deducciones: ISR: " + reporte.getISRFormateado().toString() +
                            " | Anticipos: " + reporte.getAnticiposFormateado().toString() +
                            " | Judiciales: " + reporte.getJudicialesFormateado().toString());
                    contentStream.endText();
    
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, startY - 135);
                    contentStream.showText("Total Deducciones: " + reporte.getDeduccionesFormateado().toString());
                    contentStream.endText();
    
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, startY - 150);
                    contentStream.showText("Total a Pagar: " + reporte.getTotalPagarFormateado().toString());
                    contentStream.endText();
    
                    // Espacio entre empleados
                    startY -= 180;
    
                    // Si se llega al final de la página, crear una nueva página
                    if (startY < 50) { // Margen inferior
                        contentStream.close(); // Cerrar el contenido de la página actual
    
                        // Crear una nueva página
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(helveticaBold, 12); // Usar Helvetica-Bold
                        startY = 750; // Reiniciar la posición Y
                    }
                }
    
                // Cerrar el último PDPageContentStream
                contentStream.close();
    
                // Guardar el documento en la ruta especificada
                File file = new File("C://Users//HP//OneDrive//Documentos//7mo semestre//Analisis de Sistemas 2//Proyecto-RRhh//Reportes//ReporteNomina.pdf");
                document.save(file);
    
                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(null, "Reporte PDF generado con éxito en: " + file.getAbsolutePath());
    
                // Intentar abrir el archivo automáticamente
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo automáticamente. Por favor, ábrelo manualmente.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al generar o abrir el reporte PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error inesperado al generar el reporte PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
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