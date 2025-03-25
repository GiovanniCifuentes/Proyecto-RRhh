package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import modelo.metricasModelo;
import modelo.metricasModelo.MetricaEmpleado;
import vista.metricasVista;

public class metricasControlador {
    private metricasModelo modelo;
    private metricasVista vista;

    public metricasControlador(metricasModelo modelo, metricasVista vista) {
        this.modelo = modelo;
        this.vista = vista;
        
        try {
            cargarAñosDisponibles();
        } catch (SQLException ex) {
            vista.mostrarMensaje("Error al cargar años disponibles: " + ex.getMessage());
        }
        
        vista.agregarListenerGenerarReporte(new GenerarReporteListener());
        vista.agregarListenerGenerarDatos(new GenerarDatosListener());
    }

    private void cargarAñosDisponibles() throws SQLException {
        List<Integer> años = modelo.obtenerAñosDisponibles();
        vista.setAñosDisponibles(años);
    }

    private class GenerarReporteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int año = vista.getAñoSeleccionado();
                int mes = vista.getMesSeleccionado();
                
                List<MetricaEmpleado> metricas = modelo.obtenerMetricas(año, mes);
                
                if (metricas.isEmpty()) {
                    vista.mostrarMensaje("No hay datos para el mes y año seleccionados.");
                } else {
                    vista.mostrarMetricas(metricas);
                    vista.mostrarGraficos(metricas);
                }
            } catch (SQLException ex) {
                vista.mostrarMensaje("Error al generar reporte: " + ex.getMessage());
            }
        }
    }

    private class GenerarDatosListener implements ActionListener {        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int año = vista.getAñoSeleccionado();
                int mes = vista.getMesSeleccionado();
                
                // ID de empleado hardcodeado para simplificar (en una app real sería seleccionable)
                int idEmpleado = vista.getSelectedEmpleadoId(); 
                
                modelo.generarHorasAleatorias(idEmpleado, mes, año);
                vista.mostrarMensaje("Datos generados exitosamente para el empleado " + idEmpleado);
                
                // Actualizar el reporte
                List<MetricaEmpleado> metricas = modelo.obtenerMetricas(año, mes);
                vista.mostrarMetricas(metricas);
                vista.mostrarGraficos(metricas);
            } catch (SQLException ex) {
                vista.mostrarMensaje("Error al generar datos: " + ex.getMessage());
            }
        }
    }

    private void cargarEmpleados() {
        try {
            Map<Integer, String> empleados = modelo.obtenerListaEmpleados();
            vista.setEmpleados(empleados);
        } catch (SQLException ex) {
            vista.mostrarMensaje("Error al cargar empleados: " + ex.getMessage());
        }
    }
}
