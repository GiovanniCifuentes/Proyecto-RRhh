package controlador;

import javax.swing.JOptionPane;

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
}

