package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class metricasModelo {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=RRHH;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String CONTRASEÑA = "1234";

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

    public List<MetricaEmpleado> obtenerMetricas(int año, int mes) throws SQLException {
        List<MetricaEmpleado> metricas = new ArrayList<>();
        String sql = "EXEC sp_ObtenerMetricasPorAñoMes @Año = ?, @Mes = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, año);
            stmt.setInt(2, mes);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MetricaEmpleado metrica = new MetricaEmpleado(
                        rs.getInt("IdEmpleado"),
                        rs.getString("NombreCompleto"),
                        rs.getInt("Año"),
                        rs.getInt("Mes"),
                        rs.getInt("DiasTrabajados"),
                        rs.getDouble("TotalHoras"),
                        rs.getDouble("PromedioHorasDia"),
                        rs.getDouble("HorasExtras"),
                        rs.getTime("HoraEntradaMasTemprana"),
                        rs.getTime("HoraEntradaMasTardia")
                    );
                    metricas.add(metrica);
                }
            }
        }
        return metricas;
    }

    public List<Integer> obtenerAñosDisponibles() throws SQLException {
        List<Integer> años = new ArrayList<>();
        String sql = "EXEC sp_ObtenerAñosDisponibles";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                años.add(rs.getInt("Año"));
            }
        }
        return años;
    }

    public void generarHorasAleatorias(int idEmpleado, int mes, int año) throws SQLException {
        String sql = "EXEC GenerarHorasAleatorias @IdEmpleado = ?, @Mes = ?, @Año = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idEmpleado);
            stmt.setInt(2, mes);
            stmt.setInt(3, año);
            stmt.execute();
        }
    }

    
    public Map<Integer, String> obtenerListaEmpleados() throws SQLException {
        Map<Integer, String> empleados = new LinkedHashMap<>();
        String sql = "EXEC sp_ObtenerListaEmpleados";
        
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                empleados.put(rs.getInt("IdEmpleado"), rs.getString("NombreCompleto"));
            }
        }
        return empleados;
    }

    public static class MetricaEmpleado {
        private int idEmpleado;
        private String nombreCompleto;
        private int año;
        private int mes;
        private int diasTrabajados;
        private double totalHoras;
        private double promedioHorasDia;
        private double horasExtras;
        private Time horaEntradaMasTemprana;
        private Time horaEntradaMasTardia;

        public MetricaEmpleado(int idEmpleado, String nombreCompleto, int año, int mes, 
                              int diasTrabajados, double totalHoras, double promedioHorasDia, 
                              double horasExtras, Time horaEntradaMasTemprana, Time horaEntradaMasTardia) {
            this.idEmpleado = idEmpleado;
            this.nombreCompleto = nombreCompleto;
            this.año = año;
            this.mes = mes;
            this.diasTrabajados = diasTrabajados;
            this.totalHoras = totalHoras;
            this.promedioHorasDia = promedioHorasDia;
            this.horasExtras = horasExtras;
            this.horaEntradaMasTemprana = horaEntradaMasTemprana;
            this.horaEntradaMasTardia = horaEntradaMasTardia;
        }

        // Getters
        public int getIdEmpleado() { return idEmpleado; }
        public String getNombreCompleto() { return nombreCompleto; }
        public int getAño() { return año; }
        public int getMes() { return mes; }
        public int getDiasTrabajados() { return diasTrabajados; }
        public double getTotalHoras() { return totalHoras; }
        public double getPromedioHorasDia() { return promedioHorasDia; }
        public double getHorasExtras() { return horasExtras; }
        public Time getHoraEntradaMasTemprana() { return horaEntradaMasTemprana; }
        public Time getHoraEntradaMasTardia() { return horaEntradaMasTardia; }
    }
}
