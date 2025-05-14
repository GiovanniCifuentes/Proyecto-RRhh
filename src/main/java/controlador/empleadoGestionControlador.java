package controlador;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import modelo.empleadoGestionBaja;
import modelo.empleadoGestionBaja.Liquidacion;
import vista.empleadoGestionVista;

public class empleadoGestionControlador {
    private empleadoGestionVista vista;
    private empleadoGestionBaja modelo;

    public empleadoGestionControlador(empleadoGestionVista vista, empleadoGestionBaja modelo) {
        this.vista = vista;
        this.modelo = modelo;

        this.vista.btnModificar.addActionListener(e -> modificarEmpleado());
        this.vista.btnEliminar.addActionListener(e -> eliminarEmpleado());
        this.vista.btnBuscar.addActionListener(e -> buscarEmpleado());
        this.vista.btnLimpiar.addActionListener(e -> limpiarCampos());

        // 📌 Asignar eventos a los botones
        this.vista.btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                modificarEmpleado();
            }
        });

        /*this.vista.btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //eliminarEmpleado();
                bloquearEmpleado();
            }
        });*/

        this.vista.btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buscarEmpleado();
            }
        });

        this.vista.btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                limpiarCampos();
            }
        });

        /*this.vista.btnActivar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                activarEmpleado();
            }
        });*/

        mostrarEmpleados(); // Mostrar empleados al abrir la ventana
    }

    // Método para modificar un empleado
    private void modificarEmpleado() {
        try {
            int idEmpleado = Integer.parseInt(vista.txtID.getText());
            String nombre = vista.txtNombre.getText();
            String apellido = vista.txtApellido.getText();
            String dpi = vista.txtDPI.getText();
            String fechaIngreso = ((JFormattedTextField) vista.txtFechaIngreso).getText();
            double salarioBase = Double.parseDouble(vista.txtSalarioBase.getText());
            int idRol = Integer.parseInt(vista.txtIdRol.getText());

            modelo.modificar(idEmpleado, nombre, apellido, dpi, fechaIngreso, salarioBase, idRol);
            buscarEmpleado();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al modificar el empleado: " + ex.getMessage());
        }
    }

    private void eliminarEmpleado() {
        try {
            int idEmpleado = Integer.parseInt(vista.txtID.getText());
            
            // Confirmar eliminación
            int confirmacion = JOptionPane.showConfirmDialog(vista, 
                "¿Está seguro que desea eliminar al empleado con ID: " + idEmpleado + "?\n" +
                "Se generará un documento de liquidación laboral.",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if(confirmacion == JOptionPane.YES_OPTION) {
                // Calcular liquidación antes de eliminar
                Liquidacion liquidacion = modelo.calcularLiquidacion(idEmpleado);
                
                // Generar PDF de liquidación
                generarPDFLiquidacion(liquidacion);
                
                // Proceder con la eliminación
                modelo.eliminar(idEmpleado);
                JOptionPane.showMessageDialog(vista, "Empleado eliminado correctamente.\n" +
                    "Se ha generado el documento de liquidación.", 
                    "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                
                // Actualizar lista de empleados
                mostrarEmpleados();
                limpiarCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "ID de empleado no válido", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar el empleado: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    // Método para buscar un empleado
    private void buscarEmpleado() {
        try {
            // Obtener el ID del empleado desde la Vista
            int idEmpleado = Integer.parseInt(vista.txtID.getText());
    
            // Llamar al modelo para buscar al empleado por ID
            ResultSet rs = modelo.buscar(idEmpleado);
    
            // Limpiar los datos existentes en la tabla
            DefaultTableModel modeloTabla = vista.getModeloTabla();
            modeloTabla.setRowCount(0); // Limpiar todas las filas
    
            // Procesar los datos del ResultSet
            if (rs.next()) {
                Object[] fila = {
                    rs.getInt("IdEmpleado"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getString("DPI"),
                    rs.getDate("FechaIngreso"),
                    rs.getDouble("SalarioBase"),
                    rs.getInt("IdRol")
                };
                modeloTabla.addRow(fila);
            } else {
                JOptionPane.showMessageDialog(vista, "No se encontró ningún empleado con ese ID.");
            }
    
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar el empleado: " + ex.getMessage());
        }
    }

    private void mostrarEmpleados() {
        try {
            // Llama al método del modelo para obtener los datos
            ResultSet rs = modelo.mostrarEmpleados();
    
            // Limpiar los datos existentes en la tabla
            DefaultTableModel modeloTabla = vista.getModeloTabla();
            modeloTabla.setRowCount(0); // Limpia todas las filas actuales
    
            // Procesar el ResultSet y agregar las filas a la tabla
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("IdEmpleado"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getString("DPI"),
                    rs.getDate("FechaIngreso"),
                    rs.getDouble("SalarioBase"),
                    rs.getInt("IdRol")
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar empleados: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        vista.txtID.setText("");
        vista.txtNombre.setText("");
        vista.txtApellido.setText("");
        vista.txtDPI.setText("");
        vista.txtFechaIngreso.setValue(new java.util.Date()); // Restablecer la fecha actual
        vista.txtSalarioBase.setText("");
        vista.txtIdRol.setText("");
    }

    private void generarPDFLiquidacion(Liquidacion liquidacion) throws IOException {
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
                contentStream.showText("LIQUIDACIÓN LABORAL");
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
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Nombre: " + liquidacion.getNombre() + " " + liquidacion.getApellido());
                contentStream.endText();
                yPosition -= lineHeight;

                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Fecha de Ingreso: " + liquidacion.getFechaIngreso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                contentStream.endText();
                yPosition -= lineHeight;

                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Tiempo Trabajado: " + liquidacion.getAniosTrabajados() + " años y " + 
                                     liquidacion.getMesesTrabajados() + " meses");
                contentStream.endText();
                yPosition -= lineHeight;

                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Salario Base: Q" + redondear(liquidacion.getSalarioBase()));
                contentStream.endText();
                yPosition -= lineHeight;

                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Salario Promedio (últimos 6 meses): Q" + redondear(liquidacion.getSalarioPromedio()));
                contentStream.endText();
                yPosition -= lineHeight * 2;

                // Detalle de liquidación
                contentStream.setFont(fontBold, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("2. Detalle de Liquidación");
                contentStream.endText();
                yPosition -= lineHeight;

                contentStream.setFont(fontNormal, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Indemnización (" + liquidacion.getAniosTrabajados() + " años y " + 
                                     liquidacion.getMesesTrabajados() + " meses): Q" + redondear(liquidacion.getIndemnizacion()));
                contentStream.endText();
                yPosition -= lineHeight;

                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Vacaciones no gozadas (" + liquidacion.getDiasVacaciones() + " días): Q" + 
                                     redondear(liquidacion.getVacacionesNoGozadas()));
                contentStream.endText();
                yPosition -= lineHeight;

                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Aguinaldo proporcional: Q" + redondear(liquidacion.getAguinaldo()));
                contentStream.endText();
                yPosition -= lineHeight;

                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Bono 14 proporcional: Q" + redondear(liquidacion.getBono14()));
                contentStream.endText();
                yPosition -= lineHeight;

                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Prima de antigüedad: Q" + redondear(liquidacion.getPrimaAntiguedad()));
                contentStream.endText();
                yPosition -= lineHeight * 2;

                // Total liquidación
                contentStream.setFont(fontBold, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("3. Total Liquidación");
                contentStream.endText();
                yPosition -= lineHeight;

                contentStream.setFont(fontNormal, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("TOTAL A PAGAR: Q" + redondear(liquidacion.getTotalLiquidacion()));
                contentStream.endText();
            }

            // Guardar el documento
            String nombreArchivo = "Liquidacion_" + liquidacion.getApellido() + "_" + 
                                 LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".pdf";
            File file = new File(nombreArchivo);
            document.save(file);

            // Abrir el PDF automáticamente
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        }
    }

    private String redondear(double valor) {
        BigDecimal bd = new BigDecimal(valor).setScale(2, RoundingMode.HALF_UP);
        return bd.toString();
    }

}

