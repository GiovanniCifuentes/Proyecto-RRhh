package controlador;

import java.sql.SQLException;
import java.util.List;

import modelo.reporteNominaModelo;
import modelo.reporteNominaModelo.ReporteNomina;
import vista.reporteNominaVista;

public class reporteNominaControlador {

    private reporteNominaModelo modelo;
    private reporteNominaVista vista;

    // Constructor
    public reporteNominaControlador(reporteNominaModelo modelo, reporteNominaVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Configurar acciones de los botones en la vista
        configurarAcciones();
    }

    // Método para configurar las acciones de los botones
    private void configurarAcciones() {
        // Acción para el botón "Generar Reporte PDF"
        vista.getBtnGenerarPDF().addActionListener(e -> generarReportePDF());

        // Acción para el botón "Generar Reporte Excel"
        vista.getBtnGenerarExcel().addActionListener(e -> generarReporteExcel());
    }

    // Método para generar el reporte PDF
    private void generarReportePDF() {
        try {
            // Obtener los datos del modelo
            List<ReporteNomina> reporteList = modelo.obtenerReporteNomina();
    
            // Verificar si la lista de reportes está vacía
            if (reporteList == null || reporteList.isEmpty()) {
                vista.mostrarError("No hay datos de nómina para generar el reporte.");
                return; // Salir del método si no hay datos
            }
    
            // Llamar al método de la vista para generar el PDF
            vista.generarReportePDF(reporteList);
        } catch (SQLException ex) {
            ex.printStackTrace();
            vista.mostrarError("Error al obtener los datos de la nómina: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            vista.mostrarError("Error inesperado al generar el reporte PDF: " + ex.getMessage());
        }
    }

    // Método para generar el reporte Excel
    private void generarReporteExcel() {
        try {
            // Obtener los datos del modelo
            List<ReporteNomina> reporteList = modelo.obtenerReporteNomina();

            // Llamar al método de la vista para generar el Excel
            vista.generarReporteExcel(reporteList);
        } catch (SQLException ex) {
            ex.printStackTrace();
            vista.mostrarError("Error al obtener los datos de la nómina.");
        }
    }
}
