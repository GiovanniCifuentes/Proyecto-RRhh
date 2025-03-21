package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class reporteNominaModelo {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=RRHH;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String CONTRASEÑA = "1234";

    // Método para conectar con la base de datos
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

    // Método para obtener el reporte de la nómina desde la vista
    public List<ReporteNomina> obtenerReporteNomina() throws SQLException {
        List<ReporteNomina> reporteList = new ArrayList<>();
        String sql = "SELECT * FROM VistaEmpleadosNomina";
        
        try (Connection con = conectar(); 
             PreparedStatement stmt = con.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ReporteNomina reporte = new ReporteNomina();
                reporte.setIdEmpleado(rs.getInt("IdEmpleado"));
                reporte.setNombre(rs.getString("Nombre"));
                reporte.setApellido(rs.getString("Apellido"));
                reporte.setDPI(rs.getString("DPI"));
                reporte.setFechaIngreso(rs.getDate("FechaIngreso"));
                reporte.setSalarioBase(rs.getBigDecimal("SalarioBase"));
                reporte.setEstado(rs.getString("Estado"));
                reporte.setIdNomina(rs.getInt("IdNomina"));
                reporte.setFechaPago(rs.getDate("FechaPago"));
                reporte.setSalario(rs.getBigDecimal("Salario"));
                reporte.setHorasExtras(rs.getInt("HorasExtras"));
                reporte.setComisiones(rs.getBigDecimal("Comisiones"));
                reporte.setBonificaciones(rs.getBigDecimal("Bonificaciones"));
                reporte.setValorHorasExtras(rs.getBigDecimal("ValorHorasExtras"));
                reporte.setTotalDevengado(rs.getBigDecimal("TotalDevengado"));
                reporte.setISR(rs.getBigDecimal("ISR"));
                reporte.setAnticipos(rs.getBigDecimal("Anticipos"));
                reporte.setJudiciales(rs.getBigDecimal("Judiciales"));
                reporte.setPrestamos(rs.getBigDecimal("Prestamos"));
                reporte.setIGSS(rs.getBigDecimal("IGSS"));
                reporte.setDeducciones(rs.getBigDecimal("Deducciones"));
                reporte.setTotalPagar(rs.getBigDecimal("TotalPagar"));
                
                reporteList.add(reporte);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener el reporte de nómina", e);
        }
        
        return reporteList;
    }

    // Clase interna para representar los datos del reporte de nómina
    public static class ReporteNomina {
        private int IdEmpleado;
        private String Nombre;
        private String Apellido;
        private String DPI;
        private java.sql.Date FechaIngreso;
        private java.math.BigDecimal SalarioBase;
        private String Estado;
        private int IdNomina;
        private java.sql.Date FechaPago;
        private java.math.BigDecimal Salario;
        private int HorasExtras;
        private java.math.BigDecimal Comisiones;
        private java.math.BigDecimal Bonificaciones;
        private java.math.BigDecimal ValorHorasExtras;
        private java.math.BigDecimal TotalDevengado;
        private java.math.BigDecimal ISR;
        private java.math.BigDecimal Anticipos;
        private java.math.BigDecimal Judiciales;
        private java.math.BigDecimal Prestamos;
        private java.math.BigDecimal IGSS;
        private java.math.BigDecimal Deducciones;
        private java.math.BigDecimal TotalPagar;

        // Getters y setters
        public int getIdEmpleado() { return IdEmpleado; }
        public void setIdEmpleado(int idEmpleado) { IdEmpleado = idEmpleado; }
        public String getNombre() { return Nombre; }
        public void setNombre(String nombre) { Nombre = nombre; }
        public String getApellido() { return Apellido; }
        public void setApellido(String apellido) { Apellido = apellido; }
        public String getDPI() { return DPI; }
        public void setDPI(String dPI) { DPI = dPI; }
        public java.sql.Date getFechaIngreso() { return FechaIngreso; }
        public void setFechaIngreso(java.sql.Date fechaIngreso) { FechaIngreso = fechaIngreso; }
        public java.math.BigDecimal getSalarioBase() { return SalarioBase; }
        public void setSalarioBase(java.math.BigDecimal salarioBase) { SalarioBase = salarioBase; }
        public String getEstado() { return Estado; }
        public void setEstado(String estado) { Estado = estado; }
        public int getIdNomina() { return IdNomina; }
        public void setIdNomina(int idNomina) { IdNomina = idNomina; }
        public java.sql.Date getFechaPago() { return FechaPago; }
        public void setFechaPago(java.sql.Date fechaPago) { FechaPago = fechaPago; }
        public java.math.BigDecimal getSalario() { return Salario; }
        public void setSalario(java.math.BigDecimal salario) { Salario = salario; }
        public int getHorasExtras() { return HorasExtras; }
        public void setHorasExtras(int horasExtras) { HorasExtras = horasExtras; }
        public java.math.BigDecimal getComisiones() { return Comisiones; }
        public void setComisiones(java.math.BigDecimal comisiones) { Comisiones = comisiones; }
        public java.math.BigDecimal getBonificaciones() { return Bonificaciones; }
        public void setBonificaciones(java.math.BigDecimal bonificaciones) { Bonificaciones = bonificaciones; }
        public java.math.BigDecimal getValorHorasExtras() { return ValorHorasExtras; }
        public void setValorHorasExtras(java.math.BigDecimal valorHorasExtras) { ValorHorasExtras = valorHorasExtras; }
        public java.math.BigDecimal getTotalDevengado() { return TotalDevengado; }
        public void setTotalDevengado(java.math.BigDecimal totalDevengado) { TotalDevengado = totalDevengado; }
        public java.math.BigDecimal getISR() { return ISR; }
        public void setISR(java.math.BigDecimal iSR) { ISR = iSR; }
        public java.math.BigDecimal getAnticipos() { return Anticipos; }
        public void setAnticipos(java.math.BigDecimal anticipos) { Anticipos = anticipos; }
        public java.math.BigDecimal getJudiciales() { return Judiciales; }
        public void setJudiciales(java.math.BigDecimal judiciales) { Judiciales = judiciales; }
        public java.math.BigDecimal getPrestamos() { return Prestamos; }
        public void setPrestamos(java.math.BigDecimal prestamos) { Prestamos = prestamos; }
        public java.math.BigDecimal getIGSS() { return IGSS; }
        public void setIGSS(java.math.BigDecimal iGSS) { IGSS = iGSS; }
        public java.math.BigDecimal getDeducciones() { return Deducciones; }
        public void setDeducciones(java.math.BigDecimal deducciones) { Deducciones = deducciones; }
        public java.math.BigDecimal getTotalPagar() { return TotalPagar; }
        public void setTotalPagar(java.math.BigDecimal totalPagar) { TotalPagar = totalPagar; }
    }
}
