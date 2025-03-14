package controlador;

import modelo.empleadoAlta;
import vista.empleadoAltaVista;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class empleadoAltaControlador {
    private empleadoAltaVista vista;
    private empleadoAlta modelo;

    public empleadoAltaControlador(empleadoAltaVista vista, empleadoAlta modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // 📌 Asignar evento al botón de agregar empleado
        this.vista.btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manejarAltaEmpleado();
            }
        });
    }

    private void manejarAltaEmpleado() {
        try {
            // Obtener los datos del formulario
            String nombre = vista.txtNombre.getText();
            String apellido = vista.txtApellido.getText();
            String dpi = vista.txtDPI.getText();
            String fechaIngreso = ((JFormattedTextField) vista.txtFechaIngreso).getText();
            double salarioBase = Double.parseDouble(vista.txtSalarioBase.getText());
            int idRol = Integer.parseInt(vista.txtIdRol.getText());

            // Validar que los campos no estén vacíos
            if (nombre.isEmpty() || apellido.isEmpty() || dpi.isEmpty() || fechaIngreso.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "⚠ Error: Todos los campos son obligatorios.");
                return;
            }

            // Llamar al procedimiento almacenado para dar de alta al empleado
            modelo.altaEmpleado(nombre, apellido, dpi, fechaIngreso, salarioBase, idRol);

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

    private void limpiarCampos() {
        vista.txtNombre.setText("");
        vista.txtApellido.setText("");
        vista.txtDPI.setText("");
        vista.txtFechaIngreso.setValue(new java.util.Date()); // Restablecer la fecha actual
        vista.txtSalarioBase.setText("");
        vista.txtIdRol.setText("");
    }
}