package controlador;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;

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

import modelo.opcionesNominaModelo;
import vista.opcionesNominaVista;

public class opcionesNominaControlador {

    private opcionesNominaVista vista;
    private opcionesNominaModelo modelo;
    private List<List<String>> resultadosActuales;
    private String[][] nombresColumnasActuales;

    public opcionesNominaControlador(opcionesNominaVista vista, opcionesNominaModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        inicializarControlador();
    }

    // Método para inicializar el controlador y configurar los listeners
    private void inicializarControlador() {
        vista.setBuscarListener(e -> buscarNomina());
        vista.setGenerarPDFListener(e -> generarPDF());
        vista.setGenerarExcelListener(e -> generarExcel());
    }

    private void buscarNomina() {
        try {
            int idNomina = vista.getIdNomina();
            String periodo = vista.getPeriodoSeleccionado();
    
            // Validar que se haya seleccionado un período
            if (periodo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seleccione un período (Semanal, Quincenal o Mensual).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Obtener los datos del modelo
            List<List<String>> resultados = modelo.obtenerNomina(idNomina, periodo);
    
            // Definir nombres de columnas para cada tabla
            String[][] nombresColumnas = {
                // Nombres de columnas para la Tabla 1 (Información General)
                {"ID Empleado", "Nombre", "Apellido", "DPI", "Fecha Ingreso", "Salario Base", "Fecha Pago", "Salario Mensual", "Salario Seleccionado"},
    
                // Nombres de columnas para la Tabla 2 (Desglose de Devengado y Deducciones)
                {"Horas Extras", "Comisiones", "Bonificaciones", "Valor Horas Extras", "Total Devengado Seleccionado", "ISR", "Anticipos", "Judiciales", "Prestamos", "IGSS", "Deducciones Seleccionadas"},
    
                // Nombres de columnas para la Tabla 3 (Totales a Pagar)
                {"Total a Pagar Seleccionado"}
            };
    
            // Guardar los resultados actuales para poder exportarlos
            this.resultadosActuales = resultados;
            this.nombresColumnasActuales = nombresColumnas;
    
            // Mostrar los resultados en la vista
            if (!resultados.isEmpty()) {
                vista.mostrarResultados(resultados, nombresColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron resultados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de nómina válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarPDF() {
    if (resultadosActuales == null || resultadosActuales.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay datos para generar el PDF. Realice una búsqueda primero.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try (PDDocument document = new PDDocument()) {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        // Contenido de la primera página
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // Configuración inicial
            PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDType1Font fontNormal = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            
            float margin = 50;
            float yPosition = 750;
            float lineHeight = 20;

            // Encabezado del documento
            contentStream.setFont(fontBold, 16);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Reporte de Nómina");
            contentStream.endText();
            yPosition -= lineHeight * 1.5f;

            contentStream.setFont(fontNormal, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Fecha: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            contentStream.endText();
            yPosition -= lineHeight;

            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("ID Nómina: " + vista.getIdNomina());
            contentStream.endText();
            yPosition -= lineHeight;

            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Período: " + vista.getPeriodoSeleccionado());
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
            List<String> infoEmpleado = resultadosActuales.get(0);
            for (int i = 0; i < infoEmpleado.size(); i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText(nombresColumnasActuales[0][i] + ": " + infoEmpleado.get(i));
                contentStream.endText();
                yPosition -= lineHeight;

                // Verificar si necesitamos una nueva página
                if (yPosition < 100) {
                    // Añadir una nueva página
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    try (PDPageContentStream newContentStream = new PDPageContentStream(document, page)) {
                        yPosition = 750;  // Resetear la posición para la nueva página
                    }
                }
            }

            yPosition -= lineHeight / 2;

            // Desglose de devengado y deducciones
            contentStream.setFont(fontBold, 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("2. Desglose de Devengado y Deducciones");
            contentStream.endText();
            yPosition -= lineHeight;

            contentStream.setFont(fontNormal, 12);
            List<String> desglose = resultadosActuales.get(1);
            for (int i = 0; i < desglose.size(); i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText(nombresColumnasActuales[1][i] + ": " + desglose.get(i));
                contentStream.endText();
                yPosition -= lineHeight;

                if (yPosition < 100) {
                    // Añadir una nueva página
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    try (PDPageContentStream newContentStream = new PDPageContentStream(document, page)) {
                        yPosition = 750;
                    }
                }
            }

            yPosition -= lineHeight / 2;

            // Total a pagar
            contentStream.setFont(fontBold, 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("3. Total a Pagar");
            contentStream.endText();
            yPosition -= lineHeight;

            contentStream.setFont(fontNormal, 12);
            List<String> totalPagar = resultadosActuales.get(2);
            for (int i = 0; i < totalPagar.size(); i++) {
                String valor = totalPagar.get(i);
                // Convertir el valor a BigDecimal y redondear a 2 decimales
                try {
                    BigDecimal valorDecimal = new BigDecimal(valor);
                    valorDecimal = valorDecimal.setScale(2, RoundingMode.HALF_UP);
                    String valorFormateado = "Q" + valorDecimal.toString();
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText(nombresColumnasActuales[2][i] + ": " + valorFormateado);
                    contentStream.endText();
                    yPosition -= lineHeight;
                } catch (NumberFormatException e) {
                    // Si el valor no es un número, solo se muestra como está
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText(nombresColumnasActuales[2][i] + ": " + valor);
                    contentStream.endText();
                    yPosition -= lineHeight;
                }
            }
        }

        // Guardar el documento
        String filePath = "Reporte_Nomina_" + vista.getIdNomina() + "_" +
                         LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".pdf";
        File file = new File(filePath);
        document.save(file);

        // Mostrar mensaje y abrir el PDF
        JOptionPane.showMessageDialog(null, "PDF generado con éxito: " + file.getAbsolutePath());

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}



    private void generarExcel() {
        if (resultadosActuales == null || resultadosActuales.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay datos para generar el Excel. Realice una búsqueda primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            // Crear una hoja para cada sección de datos
            Sheet sheetInfo = workbook.createSheet("Información Empleado");
            Sheet sheetDesglose = workbook.createSheet("Desglose");
            Sheet sheetTotal = workbook.createSheet("Total a Pagar");

            // Escribir datos de información del empleado
            Row headerRowInfo = sheetInfo.createRow(0);
            for (int i = 0; i < nombresColumnasActuales[0].length; i++) {
                headerRowInfo.createCell(i).setCellValue(nombresColumnasActuales[0][i]);
            }
            Row dataRowInfo = sheetInfo.createRow(1);
            for (int i = 0; i < resultadosActuales.get(0).size(); i++) {
                dataRowInfo.createCell(i).setCellValue(resultadosActuales.get(0).get(i));
            }

            // Escribir datos de desglose
            Row headerRowDesglose = sheetDesglose.createRow(0);
            for (int i = 0; i < nombresColumnasActuales[1].length; i++) {
                headerRowDesglose.createCell(i).setCellValue(nombresColumnasActuales[1][i]);
            }
            Row dataRowDesglose = sheetDesglose.createRow(1);
            for (int i = 0; i < resultadosActuales.get(1).size(); i++) {
                dataRowDesglose.createCell(i).setCellValue(resultadosActuales.get(1).get(i));
            }

            // Escribir datos de total a pagar
            Row headerRowTotal = sheetTotal.createRow(0);
            for (int i = 0; i < nombresColumnasActuales[2].length; i++) {
                headerRowTotal.createCell(i).setCellValue(nombresColumnasActuales[2][i]);
            }
            Row dataRowTotal = sheetTotal.createRow(1);
            for (int i = 0; i < resultadosActuales.get(2).size(); i++) {
                dataRowTotal.createCell(i).setCellValue(resultadosActuales.get(2).get(i));
            }

            // Autoajustar el ancho de las columnas
            for (int i = 0; i < nombresColumnasActuales[0].length; i++) {
                sheetInfo.autoSizeColumn(i);
            }
            for (int i = 0; i < nombresColumnasActuales[1].length; i++) {
                sheetDesglose.autoSizeColumn(i);
            }
            for (int i = 0; i < nombresColumnasActuales[2].length; i++) {
                sheetTotal.autoSizeColumn(i);
            }

            // Guardar el archivo
            String filePath = "Reporte_Nomina_" + vista.getIdNomina() + "_" + 
                            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".xlsx";
            File file = new File(filePath);
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
            }

            JOptionPane.showMessageDialog(null, "Excel generado con éxito: " + file.getAbsolutePath());
            
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el Excel: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}