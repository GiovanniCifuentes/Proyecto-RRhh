package controlador;

import java.util.List;

import javax.swing.JOptionPane;

import modelo.opcionesNominaModelo;
import vista.opcionesNominaVista;

public class opcionesNominaControlador {

    private opcionesNominaVista vista;
    private opcionesNominaModelo modelo;

    public opcionesNominaControlador(opcionesNominaVista vista, opcionesNominaModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        inicializarControlador();
    }

    // Método para inicializar el controlador y configurar los listeners
    private void inicializarControlador() {
        vista.setBuscarListener(e -> buscarNomina());
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
}