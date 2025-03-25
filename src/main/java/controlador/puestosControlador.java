package controlador;

import vista.puestosVista;
import modelo.puestosModelo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class puestosControlador {
    private puestosVista vista;
    private puestosModelo modelo;

    public puestosControlador(puestosVista vista, puestosModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // 📌 Asignar eventos a los botones
        this.vista.btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ingresarRol();
            }
        });

        this.vista.btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                modificarRol();
            }
        });

        this.vista.btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                eliminarRol();
            }
        });

        this.vista.btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buscarRol();
            }
        });

        this.vista.btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                limpiarCampos();
            }
        });

        mostrarEmpleados(); // Mostrar empleados al abrir la ventana
    }

    private void ingresarRol() {
        try {
            // Obtener los datos del formulario
            String puesto = vista.txtPuesto.getText();
            double salarioBase = Double.parseDouble(vista.txtSalarioPuesto.getText());

            // Validar que los campos no estén vacíos
            if (puesto.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "⚠ Error: Todos los campos son obligatorios.");
                return;
            }

            // Llamar al procedimiento almacenado para ingresar el rol
            modelo.ingresar(puesto, salarioBase);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(vista, "Empleado agregado con éxito.");

            // Limpiar los campos del formulario
            limpiarCampos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "⚠ Error: Verifique que los campos numéricos estén correctamente ingresados.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "⚠ Error: No se pudo dar de alta al empleado.");
        }
    }

    // Método para modificar un rol
    private void modificarRol() {
        try {
            int idRol = Integer.parseInt(vista.txtIdRol.getText());
            String puesto = vista.txtPuesto.getText();
            double salarioPuesto = Double.parseDouble(vista.txtSalarioPuesto.getText());

            modelo.modificar(idRol, puesto, salarioPuesto);
            buscarRol();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al modificar el empleado: " + ex.getMessage());
        }
    }

    // Método para eliminar un rol
    private void eliminarRol() {
        try {
            int idRol = Integer.parseInt(vista.txtIdRol.getText());
            modelo.eliminar(idRol);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar el empleado: " + ex.getMessage());
        }
    }

    // Método para buscar un rol
    private void buscarRol() {
        try {
            // Obtener el ID del rol desde la Vista
            int idRol = Integer.parseInt(vista.txtIdRol.getText());
    
            // Llamar al modelo para buscar al rol por ID
            ResultSet rs = modelo.buscar(idRol);
    
            // Limpiar los datos existentes en la tabla
            DefaultTableModel modeloTabla = vista.getModeloTabla();
            modeloTabla.setRowCount(0); // Limpiar todas las filas
    
            // Procesar los datos del ResultSet
            if (rs.next()) {
                Object[] fila = {
                    rs.getInt("IdRol"),
                    rs.getString("NombreRol"),
                    rs.getDouble("SalarioRol"),
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
            ResultSet rs = modelo.mostrarRoles();
    
            // Limpiar los datos existentes en la tabla
            DefaultTableModel modeloTabla = vista.getModeloTabla();
            modeloTabla.setRowCount(0); // Limpia todas las filas actuales
    
            // Procesar el ResultSet y agregar las filas a la tabla
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("IdRol"),
                    rs.getString("NombreRol"),
                    rs.getDouble("SalarioRol"),
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar empleados: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        vista.txtIdRol.setText("");
        vista.txtPuesto.setText("");
        vista.txtSalarioPuesto.setText("");
    }

}