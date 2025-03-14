package modelo;

import java.sql.*;

public class UsuariosModelo {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=RRHH;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String CONTRASEÑA = "1234";

    // Método para conectar con la base de datos
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

    // Método para insertar un nuevo usuario
    public boolean insertarUsuario(String nombreUsuario, String contraseñaUsuario) {
        String sql = "{CALL InsertarUsuario(?, ?)}";
        
        try (Connection conexion = conectar();
             CallableStatement stmt = conexion.prepareCall(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contraseñaUsuario);

            stmt.execute();
            System.out.println("✅ Usuario insertado correctamente.");
            return true; // Inserción exitosa
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar el usuario: " + e.getMessage());
            return false; // Inserción fallida
        }
    }

    // Método para modificar un usuario
    public boolean modificarUsuario(int idUsuario, String nuevoNombreUsuario, String nuevaContraseñaUsuario, String contraseñaActual) {
        String sql = "{CALL ModificarUsuario(?, ?, ?, ?)}";
        
        try (Connection conexion = conectar();
             CallableStatement stmt = conexion.prepareCall(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setString(2, nuevoNombreUsuario);
            stmt.setString(3, nuevaContraseñaUsuario);
            stmt.setString(4, contraseñaActual);

            stmt.execute();
            System.out.println("✅ Usuario modificado correctamente.");
            return true; // Modificación exitosa
        } catch (SQLException e) {
            System.out.println("❌ Error al modificar el usuario: " + e.getMessage());
            return false; // Modificación fallida
        }
    }

    // Método para eliminar un usuario
    public boolean eliminarUsuario(int idUsuario, String contraseñaActual) {
        String sql = "{CALL EliminarUsuario(?, ?)}";
        
        try (Connection conexion = conectar();
             CallableStatement stmt = conexion.prepareCall(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setString(2, contraseñaActual);

            stmt.execute();
            System.out.println("✅ Usuario eliminado correctamente.");
            return true; // Eliminación exitosa
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar el usuario: " + e.getMessage());
            return false; // Eliminación fallida
        }
    }
}
