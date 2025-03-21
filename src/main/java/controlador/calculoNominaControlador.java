package controlador;

import java.util.ArrayList;

import modelo.NominaVO;
import modelo.calculoNominaModelo;
import vista.calculoNominaVista;

public class calculoNominaControlador {
    private calculoNominaModelo modelo;
    private calculoNominaVista vista;

    public calculoNominaControlador(calculoNominaModelo modelo, calculoNominaVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Configurar acciones de los botones
        configurarAcciones();
    }

    private void configurarAcciones() {
        // Configurar acción para el botón "Crear Nómina"
        vista.getBtnCrear().addActionListener(e -> crearNomina());

        // Configurar acción para el botón "Actualizar Nómina"
        vista.getBtnActualizar().addActionListener(e -> actualizarNomina());

        // Configurar acción para el botón "Eliminar Nómina"
        vista.getBtnEliminar().addActionListener(e -> eliminarNomina());

        // Configurar acción para el botón "Buscar por ID"
        vista.getBtnBuscar().addActionListener(e -> buscarNomina());
    }

    private void crearNomina() {
        try {
            // Obtener datos de la vista
            int idEmpleado = vista.getTxtIdEmpleado();
            String fechaPago = vista.getTxtFechaPago();
            //double salario = vista.getTxtSalario();
            Integer horasExtras = vista.getTxtHorasExtras();
            Double comisiones = vista.getTxtComisiones();
            Double bonificaciones = vista.getTxtBonificaciones();
            Double isr = vista.getTxtISR();
            Double anticipos = vista.getTxtAnticipos();
            Double judiciales = vista.getTxtJudiciales();
            Double prestamos = vista.getTxtPrestamos();

            // Crear objeto NominaVO
            NominaVO nomina = new NominaVO();
            nomina.setIdEmpleado(idEmpleado);
            nomina.setFechaPago(java.sql.Date.valueOf(fechaPago));
            //nomina.setSalario(salario);
            nomina.setHorasExtras(horasExtras);
            nomina.setComisiones(comisiones);
            nomina.setBonificaciones(bonificaciones);
            nomina.setIsr(isr);
            nomina.setAnticipos(anticipos);
            nomina.setJudiciales(judiciales);
            nomina.setPrestamos(prestamos);

            // Llamar al modelo para crear la nómina
            modelo.crearNomina(nomina);

            // Actualizar la vista
            cargarNominas();
            vista.mostrarMensaje("Nómina creada exitosamente.");
        } catch (Exception ex) {
            vista.mostrarError("Error al crear nómina: " + ex.getMessage());
        }
    }

    private void actualizarNomina() {
        try {
            // Obtener datos de la vista
            int idNomina = vista.getTxtIdNomina();
            int idEmpleado = vista.getTxtIdEmpleado();
            String fechaPago = vista.getTxtFechaPago();
            double salario = vista.getTxtSalario();
            Integer horasExtras = vista.getTxtHorasExtras();
            Double comisiones = vista.getTxtComisiones();
            Double bonificaciones = vista.getTxtBonificaciones();
            Double isr = vista.getTxtISR();
            Double anticipos = vista.getTxtAnticipos();
            Double judiciales = vista.getTxtJudiciales();
            Double prestamos = vista.getTxtPrestamos();

            // Crear objeto NominaVO
            NominaVO nomina = new NominaVO();
            nomina.setIdNomina(idNomina);
            nomina.setIdEmpleado(idEmpleado);
            nomina.setFechaPago(java.sql.Date.valueOf(fechaPago));
            nomina.setSalario(salario);
            nomina.setHorasExtras(horasExtras);
            nomina.setComisiones(comisiones);
            nomina.setBonificaciones(bonificaciones);
            nomina.setIsr(isr);
            nomina.setAnticipos(anticipos);
            nomina.setJudiciales(judiciales);
            nomina.setPrestamos(prestamos);

            // Llamar al modelo para actualizar la nómina
            modelo.actualizarNomina(nomina);

            // Actualizar la vista
            cargarNominas();
            vista.mostrarMensaje("Nómina actualizada exitosamente.");
        } catch (Exception ex) {
            vista.mostrarError("Error al actualizar nómina: " + ex.getMessage());
        }
    }

    private void eliminarNomina() {
        try {
            // Obtener ID de la nómina desde la vista
            int idNomina = vista.getTxtIdNomina();

            // Llamar al modelo para eliminar la nómina
            modelo.eliminarNomina(idNomina);

            // Actualizar la vista
            cargarNominas();
            vista.mostrarMensaje("Nómina eliminada exitosamente.");
        } catch (Exception ex) {
            vista.mostrarError("Error al eliminar nómina: " + ex.getMessage());
        }
    }

    private void buscarNomina() {
        try {
            // Obtener ID de la nómina desde la vista
            int idNomina = vista.getTxtIdNomina();

            // Llamar al modelo para buscar la nómina
            NominaVO nomina = modelo.obtenerNominaPorId(idNomina);

            if (nomina != null) {
                // Mostrar los datos en la vista
                vista.mostrarNomina(nomina);
            } else {
                vista.mostrarError("Nómina no encontrada.");
            }
        } catch (Exception ex) {
            vista.mostrarError("Error al buscar nómina: " + ex.getMessage());
        }
    }

    private void cargarNominas() {
        // Obtener todas las nóminas desde el modelo
        ArrayList<NominaVO> nominas = modelo.leerNominas(null, null);

        // Actualizar la vista con las nóminas
        vista.mostrarNominas(nominas);
    }
}
