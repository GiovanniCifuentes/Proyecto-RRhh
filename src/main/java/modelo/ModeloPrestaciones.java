package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

// Modelo
public class ModeloPrestaciones {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=RRHH;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String CONTRASEÑA = "1234";

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

    public DefaultTableModel obtenerPrestaciones() {
        DefaultTableModel modelo = new DefaultTableModel();
        try (Connection con = conectar(); Statement stmt = con.createStatement()) {
            String consulta = "SELECT * FROM Prestaciones";
            ResultSet rs = stmt.executeQuery(consulta);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnas = metaData.getColumnCount();

            for (int i = 1; i <= columnas; i++) {
                modelo.addColumn(metaData.getColumnName(i));
            }
            while (rs.next()) {
                Vector<Object> fila = new Vector<>();
                for (int i = 1; i <= columnas; i++) {
                    fila.add(rs.getObject(i));
                }
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelo;
    }

    public void descontarVacaciones(int idEmpleado, double diasTomados) {
        try (Connection con = conectar();
             CallableStatement cs = con.prepareCall("{call spActualizarVacaciones(?, ?)}")) {
            cs.setInt(1, idEmpleado);
            cs.setDouble(2, diasTomados);
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void calcularPrestaciones() {
        try (Connection con = conectar();
             CallableStatement cs = con.prepareCall("{call CalcularPrestaciones}")) {
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
