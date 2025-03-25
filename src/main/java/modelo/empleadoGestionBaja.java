package modelo;

import java.sql.*;

public class empleadoGestionBaja {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=RRHH;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String CONTRASEÑA = "1234";

    // Método para conectar con la base de datos
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

    // Método para ejecutar el procedimiento almacenado ModificarEmpleado
    public void modificar(int idEmpleado, String nombre, String apellido, String dpi, String fechaIngreso, Double salarioBase, Integer idRol) {
        String sql = "{CALL ModificarEmpleado(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conexion = conectar();
             CallableStatement stmt = conexion.prepareCall(sql)) {
    
            stmt.setInt(1, idEmpleado);
            stmt.setString(2, nombre);
            stmt.setString(3, apellido);
            stmt.setString(4, dpi);
            stmt.setDate(5, Date.valueOf(fechaIngreso));
            stmt.setDouble(6, salarioBase);
            stmt.setInt(7, idRol);

            int filasAfectadas = stmt.executeUpdate();
            System.out.println("✅ Alta de empleado realizada. Filas afectadas: " + filasAfectadas);
        } catch (SQLException e) {
            System.out.println("❌ Error al ejecutar la alta de empleado");
            e.printStackTrace();
        }
    }

    // Método para ejecutar el procedimiento almacenado EliminarEmpleado
    public void eliminar(int idEmpleado) {
        String sql = "{CALL EliminarEmpleado(?)}";

        try (Connection conexion = conectar();
                CallableStatement stmt = conexion.prepareCall(sql)) {

            stmt.setInt(1, idEmpleado);

            int filasAfectadas = stmt.executeUpdate();
            System.out.println("✅ Eliminación de empleado realizada. Filas afectadas: " + filasAfectadas);
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar empleado");
            e.printStackTrace();
        }
    }


    // Método para ejecutar el procedimiento almacenado BuscarEmpleado
    public ResultSet buscar(int idEmpleado) throws SQLException {
        String sql = "{CALL BuscarEmpleado(?)}"; // Llamada al procedimiento almacenado
        Connection conexion = conectar();
    
        CallableStatement stmt = conexion.prepareCall(sql);
        stmt.setInt(1, idEmpleado); // Pasar el parámetro ID al procedimiento almacenado
    
        return stmt.executeQuery(); // Devuelve el ResultSet para procesarlo en el Controlador
        
    }

    public ResultSet mostrarEmpleados() throws SQLException {
        String sql = "{CALL MostrarEmpleados}"; // Llamada al procedimiento almacenado
        Connection conexion = conectar();
    
        // Ejecutar la consulta
        CallableStatement stmt = conexion.prepareCall(sql);
        return stmt.executeQuery(); // Devuelve el ResultSet para procesarlo en el Controlador
    }
}
