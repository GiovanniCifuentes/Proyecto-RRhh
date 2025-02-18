package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQL {
    public static void main(String[] args) {
        // URL de conexión: jdbc:sqlserver://<SERVIDOR>:<PUERTO>;databaseName=<BD>
        String url = "jdbc:sqlserver://localhost:1433;databaseName=RRHH;encrypt=true;trustServerCertificate=true";

        String usuario = "sa";  // Usuario de SQL Server
        String contraseña = "1234";  // Contraseña del usuario

        try {
            // Cargar el driver JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establecer conexión
            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("✅ Conexión exitosa a la base de datos SQL Server");

            // Cierra la conexión
            conexion.close();
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error: No se encontró el driver JDBC");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión con la base de datos");
            e.printStackTrace();
        }
    }
}
