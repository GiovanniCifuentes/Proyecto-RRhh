package controlador;

import vista.empleadoGestionVista;
import modelo.empleadoGestionBaja;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class empleadoGestionControlador {
    private empleadoGestionVista vista;
    private empleadoGestionBaja modelo;

    public empleadoGestionControlador(empleadoGestionVista vista, empleadoGestionBaja modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // 📌 Asignar eventos a los botones
        this.vista.btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                modificarEmpleado();
            }
        });

        this.vista.btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                eliminarEmpleado();
            }
        });

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

    // Método para eliminar un empleado
    private void eliminarEmpleado() {
        try {
            int idEmpleado = Integer.parseInt(vista.txtID.getText());
            modelo.eliminar(idEmpleado);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar el empleado: " + ex.getMessage());
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

}

