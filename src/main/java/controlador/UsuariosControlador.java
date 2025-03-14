package controlador;

import modelo.UsuariosModelo;
import vista.UsuariosVista;

public class UsuariosControlador {
    private UsuariosVista vista;
    private UsuariosModelo modelo;

    public UsuariosControlador(UsuariosVista vista, UsuariosModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // Asignar eventos a los botones de la vista
        this.vista.getBtnInsertar().addActionListener(e -> insertarUsuario());
        this.vista.getBtnModificar().addActionListener(e -> modificarUsuario());
        this.vista.getBtnEliminar().addActionListener(e -> eliminarUsuario());
    }

    // Método para insertar un usuario
    private void insertarUsuario() {
        String nombreUsuario = vista.getTxtNombreUsuario().getText();
        String contraseñaUsuario = vista.getTxtContraseñaUsuario().getText();

        if (nombreUsuario.isEmpty() || contraseñaUsuario.isEmpty()) {
            vista.mostrarMensaje("⚠ Error: Todos los campos son obligatorios.");
            return;
        }

        boolean resultado = modelo.insertarUsuario(nombreUsuario, contraseñaUsuario);
        if (resultado) {
            vista.mostrarMensaje("✅ Usuario insertado correctamente.");
            vista.limpiarCampos(); // Limpiar los campos después de la inserción
        } else {
            vista.mostrarMensaje("❌ Error: No se pudo insertar el usuario.");
        }
    }

    // Método para modificar un usuario
    private void modificarUsuario() {
        try {
            int idUsuario = Integer.parseInt(vista.getTxtIdUsuario().getText());
            String nuevoNombreUsuario = vista.getTxtNombreUsuario().getText();
            String nuevaContraseñaUsuario = vista.getTxtNuevaContraseñaUsuario().getText();
            String contraseñaActual = vista.getTxtContraseñaActual().getText();

            if (nuevoNombreUsuario.isEmpty() || nuevaContraseñaUsuario.isEmpty() || contraseñaActual.isEmpty()) {
                vista.mostrarMensaje("⚠ Error: Todos los campos son obligatorios.");
                return;
            }

            boolean resultado = modelo.modificarUsuario(idUsuario, nuevoNombreUsuario, nuevaContraseñaUsuario, contraseñaActual);
            if (resultado) {
                vista.mostrarMensaje("✅ Usuario modificado correctamente.");
                vista.limpiarCampos(); // Limpiar los campos después de la modificación
            } else {
                vista.mostrarMensaje("❌ Error: No se pudo modificar el usuario.");
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("⚠ Error: El ID de usuario debe ser un número.");
        }
    }

    // Método para eliminar un usuario
    private void eliminarUsuario() {
        try {
            int idUsuario = Integer.parseInt(vista.getTxtIdUsuario().getText());
            String contraseñaActual = vista.getTxtContraseñaActual().getText();

            if (contraseñaActual.isEmpty()) {
                vista.mostrarMensaje("⚠ Error: La contraseña actual es obligatoria.");
                return;
            }

            boolean resultado = modelo.eliminarUsuario(idUsuario, contraseñaActual);
            if (resultado) {
                vista.mostrarMensaje("✅ Usuario eliminado correctamente.");
                vista.limpiarCampos(); // Limpiar los campos después de la eliminación
            } else {
                vista.mostrarMensaje("❌ Error: No se pudo eliminar el usuario.");
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("⚠ Error: El ID de usuario debe ser un número.");
        }
    }
}
