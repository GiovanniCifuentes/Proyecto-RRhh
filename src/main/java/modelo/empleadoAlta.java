package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class empleadoAlta {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=RRHH;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String CONTRASEÑA = "1234";

    // Método para conectar con la base de datos
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

    // Método para ejecutar el procedimiento almacenado AltaEmpleado
    public void altaEmpleado(String nombre, String apellido, String dpi, String fechaIngreso, Double salarioBase, Integer idRol) {
        String sql = "{CALL AltaEmpleado(?, ?, ?, ?, ?, ?)}";
        
        try (Connection conexion = conectar();
             CallableStatement stmt = conexion.prepareCall(sql)) {
    
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, dpi);
            stmt.setDate(4, Date.valueOf(fechaIngreso));
            stmt.setDouble(5, salarioBase);
            stmt.setInt(6, idRol);
    
            stmt.executeUpdate();
            System.out.println("✅ Alta de empleado realizada correctamente");
        } catch (SQLException e) {
            if (e.getMessage().contains("Violation of UNIQUE KEY constraint")) {
                System.out.println("❌ Error: Ya existe un empleado con el DPI " + dpi);
            } else {
                System.out.println("❌ Error al ejecutar la alta de empleado");
                e.printStackTrace();
            }
        }
    }
}