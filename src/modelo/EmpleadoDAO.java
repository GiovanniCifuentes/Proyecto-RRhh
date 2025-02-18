package modelo;

import java.sql.*;

public class EmpleadoDAO {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=RRHH;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String CONTRASEÑA = "1234";

    // Método para conectar con la base de datos
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

    // Método para ejecutar el procedimiento almacenado
    public void gestionarEmpleado(String operacion, Integer estoid, String nombre, String puesto, Double salario) {
        String sql = "{CALL sp_GestionarEmpleados(?, ?, ?, ?, ?)}";

        try (Connection conexion = conectar();
             CallableStatement stmt = conexion.prepareCall(sql)) {

            // Configurar parámetros
            stmt.setString(1, operacion);
            if (estoid != null) stmt.setInt(2, estoid);
            else stmt.setNull(2, Types.INTEGER);

            if (nombre != null) stmt.setString(3, nombre);
            else stmt.setNull(3, Types.VARCHAR);

            if (puesto != null) stmt.setString(4, puesto);
            else stmt.setNull(4, Types.VARCHAR);

            if (salario != null) stmt.setDouble(5, salario);
            else stmt.setNull(5, Types.DECIMAL);

            // Ejecutar el procedimiento almacenado
            if (operacion.equalsIgnoreCase("SELECT")) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("estoid") + 
                                       ", Nombre: " + rs.getString("nombre") +
                                       ", Puesto: " + rs.getString("puesto") +
                                       ", Salario: " + rs.getDouble("salario"));
                }
            } else {
                int filasAfectadas = stmt.executeUpdate();
                System.out.println("✅ Operación " + operacion + " realizada. Filas afectadas: " + filasAfectadas);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al ejecutar la operación: " + operacion);
            e.printStackTrace();
        }
    }
}
