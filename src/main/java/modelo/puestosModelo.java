package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class puestosModelo {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=RRHH;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String CONTRASEÑA = "1234";

    // Método para conectar con la base de datos
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

    public int ingresar(String nombre) {
        String sql = "{CALL IngresarRol(?)}";
        
        try (Connection conexion = conectar();
             CallableStatement stmt = conexion.prepareCall(sql)) {
    
            stmt.setString(1, nombre);  // Solo el nombre
    
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt("IdRolNuevo");
                    System.out.println("✅ Rol ingresado correctamente. ID generado: " + idGenerado);
                    return idGenerado;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al ingresar rol: " + e.getMessage());
            e.printStackTrace();
        }
        return -1; // Retorna -1 si falla
    }

    // Método para ejecutar el procedimiento almacenado AltaEmpleado
    /*public void ingresar(int idRol, String nombre, Double salarioBase) {
        String sql = "{CALL IngresarRol(?, ?)}";
        
        try (Connection conexion = conectar();
             CallableStatement stmt = conexion.prepareCall(sql)) {

            stmt.setInt(1, idRol);
            stmt.setString(1, nombre);
            //stmt.setDouble(2, salarioBase);

            int filasAfectadas = stmt.executeUpdate();
            System.out.println("✅ Alta de empleado realizada. Filas afectadas: " + filasAfectadas);
        } catch (SQLException e) {
            System.out.println("❌ Error al ejecutar la alta de empleado");
            e.printStackTrace();
        }
    }*/

    // Método para ejecutar el procedimiento almacenado ModificarRol
    public void modificar(int idRol, String nombre/*, Double salarioBase*/) {
        String sql = "{CALL ModificarRol(?, ?)}";
        try (Connection conexion = conectar();
             CallableStatement stmt = conexion.prepareCall(sql)) {
    
            stmt.setInt(1, idRol);
            stmt.setString(2, nombre);
           // stmt.setDouble(3, salarioBase);

            int filasAfectadas = stmt.executeUpdate();
            System.out.println("✅ Alta de empleado realizada. Filas afectadas: " + filasAfectadas);
        } catch (SQLException e) {
            System.out.println("❌ Error al ejecutar la alta de empleado");
            e.printStackTrace();
        }
    }

    // Método para ejecutar el procedimiento almacenado EliminarRol
    public void eliminar(int idRol) {
        String sql = "{CALL EliminarRol(?)}";

        try (Connection conexion = conectar();
                CallableStatement stmt = conexion.prepareCall(sql)) {

            stmt.setInt(1, idRol);

            int filasAfectadas = stmt.executeUpdate();
            System.out.println("✅ Eliminación de empleado realizada. Filas afectadas: " + filasAfectadas);
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar empleado");
            e.printStackTrace();
        }
    }


    // Método para ejecutar el procedimiento almacenado BuscarRol
    public ResultSet buscar(int idRol) throws SQLException {
        String sql = "{CALL BuscarRol(?)}"; // Llamada al procedimiento almacenado
        Connection conexion = conectar();
    
        CallableStatement stmt = conexion.prepareCall(sql);
        stmt.setInt(1, idRol); // Pasar el parámetro ID al procedimiento almacenado
    
        return stmt.executeQuery(); // Devuelve el ResultSet para procesarlo en el Controlador
        
    }

    public ResultSet mostrarRoles() throws SQLException {
        String sql = "{CALL MostrarRoles}"; // Llamada al procedimiento almacenado
        Connection conexion = conectar();
    
        // Ejecutar la consulta
        CallableStatement stmt = conexion.prepareCall(sql);
        return stmt.executeQuery(); // Devuelve el ResultSet para procesarlo en el Controlador
    }
}