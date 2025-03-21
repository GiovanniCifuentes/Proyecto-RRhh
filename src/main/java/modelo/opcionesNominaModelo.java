package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class opcionesNominaModelo {

    // Datos de conexión a SQL Server
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=RRHH;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String CONTRASEÑA = "1234";

    // Método para conectar con la base de datos
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

    // Método para ejecutar el procedimiento almacenado obtenerNomina
    public List<List<String>> obtenerNomina(int idNomina, String periodo) {
        List<List<String>> resultados = new ArrayList<>();
        String sql = "{CALL obtenerNomina(?, ?)}";

        try (Connection conn = conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idNomina);
            stmt.setString(2, periodo);

            boolean tieneResultados = stmt.execute();

            // Procesar los resultados de las tres tablas
            while (tieneResultados) {
                try (ResultSet rs = stmt.getResultSet()) {
                    List<String> fila = new ArrayList<>();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    while (rs.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            fila.add(rs.getString(i));
                        }
                    }
                    resultados.add(fila);
                }
                tieneResultados = stmt.getMoreResults();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    // Método para obtener la información general del empleado y nómina
    public List<String> obtenerInformacionGeneral(int idNomina, String periodo) {
        List<List<String>> resultados = obtenerNomina(idNomina, periodo);
        return resultados.isEmpty() ? new ArrayList<>() : resultados.get(0);
    }

    // Método para obtener el desglose de devengado y deducciones
    public List<String> obtenerDesglose(int idNomina, String periodo) {
        List<List<String>> resultados = obtenerNomina(idNomina, periodo);
        return resultados.size() < 2 ? new ArrayList<>() : resultados.get(1);
    }

    // Método para obtener los totales a pagar
    public List<String> obtenerTotales(int idNomina, String periodo) {
        List<List<String>> resultados = obtenerNomina(idNomina, periodo);
        return resultados.size() < 3 ? new ArrayList<>() : resultados.get(2);
    }
}