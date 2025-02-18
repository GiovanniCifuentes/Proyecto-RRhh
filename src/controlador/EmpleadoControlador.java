package controlador;

import modelo.EmpleadoDAO;
import vista.EmpleadoVista;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmpleadoControlador {
    private EmpleadoVista vista;
    private EmpleadoDAO modelo;

    public EmpleadoControlador(EmpleadoVista vista, EmpleadoDAO modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // 📌 Asignar eventos a los botones
        this.vista.btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manejarOperacion("INSERT");
            }
        });

        this.vista.btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manejarOperacion("UPDATE");
            }
        });

        this.vista.btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manejarOperacion("DELETE");
            }
        });

        this.vista.btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarEmpleados();
            }
        });

        cargarEmpleados(); // Mostrar empleados al abrir la ventana
    }

    private void manejarOperacion(String operacion) {
        try {
            Integer estoid = vista.txtId.getText().isEmpty() ? null : Integer.parseInt(vista.txtId.getText());
            String nombre = vista.txtNombre.getText();
            String puesto = vista.txtPuesto.getText();
            Double salario = vista.txtSalario.getText().isEmpty() ? null : Double.parseDouble(vista.txtSalario.getText());

            modelo.gestionarEmpleado(operacion, estoid, nombre, puesto, salario);
            JOptionPane.showMessageDialog(vista, "Operación " + operacion + " realizada con éxito.");
            cargarEmpleados();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "⚠ Error: Verifique los datos ingresados.");
        }
    }

    private void cargarEmpleados() {
        try {
            Connection conexion = modelo.conectar();
            CallableStatement stmt = conexion.prepareCall("{CALL sp_GestionarEmpleados('SELECT', NULL, NULL, NULL, NULL)}");
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel modeloTabla = (DefaultTableModel) vista.tablaEmpleados.getModel();
            modeloTabla.setRowCount(0); // Limpiar tabla antes de actualizar

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                        rs.getInt("estoid"),
                        rs.getString("nombre"),
                        rs.getString("puesto"),
                        rs.getDouble("salario")
                });
            }

            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista, "⚠ Error al cargar empleados.");
        }
    }

}

