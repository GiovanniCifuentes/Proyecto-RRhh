package modelo;

import java.math.BigDecimal;
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

        // Método auxiliar para formatear cantidades monetarias
        private String formatoMonetario(BigDecimal valor) {
            if (valor == null) {
                return "Q0.00";
            }
            // Formatea con 2 decimales, separador de miles y símbolo Q
            return "Q" + String.format("%,.2f", valor);
        }

        // Getters formateados para cantidades monetarias
        public String getSalarioBaseFormateado() { return formatoMonetario(SalarioBase); }
        public String getSalarioFormateado() { return formatoMonetario(Salario); }
        public String getComisionesFormateado() { return formatoMonetario(Comisiones); }
        public String getBonificacionesFormateado() { return formatoMonetario(Bonificaciones); }
        public String getValorHorasExtrasFormateado() { return formatoMonetario(ValorHorasExtras); }
        public String getTotalDevengadoFormateado() { return formatoMonetario(TotalDevengado); }
        public String getISRFormateado() { return formatoMonetario(ISR); }
        public String getAnticiposFormateado() { return formatoMonetario(Anticipos); }
        public String getJudicialesFormateado() { return formatoMonetario(Judiciales); }
        public String getPrestamosFormateado() { return formatoMonetario(Prestamos); }
        public String getIGSSFormateado() { return formatoMonetario(IGSS); }
        public String getDeduccionesFormateado() { return formatoMonetario(Deducciones); }
        public String getTotalPagarFormateado() { return formatoMonetario(TotalPagar); }

        // Getters originales
        public int getIdEmpleado() { return IdEmpleado; }
        public String getNombre() { return Nombre; }
        public String getApellido() { return Apellido; }
        public String getDPI() { return DPI; }
        public java.sql.Date getFechaIngreso() { return FechaIngreso; }
        public java.math.BigDecimal getSalarioBase() { return SalarioBase; }
        public String getEstado() { return Estado; }
        public int getIdNomina() { return IdNomina; }
        public java.sql.Date getFechaPago() { return FechaPago; }
        public java.math.BigDecimal getSalario() { return Salario; }
        public int getHorasExtras() { return HorasExtras; }
        public java.math.BigDecimal getComisiones() { return Comisiones; }
        public java.math.BigDecimal getBonificaciones() { return Bonificaciones; }
        public java.math.BigDecimal getValorHorasExtras() { return ValorHorasExtras; }
        public java.math.BigDecimal getTotalDevengado() { return TotalDevengado; }
        public java.math.BigDecimal getISR() { return ISR; }
        public java.math.BigDecimal getAnticipos() { return Anticipos; }
        public java.math.BigDecimal getJudiciales() { return Judiciales; }
        public java.math.BigDecimal getPrestamos() { return Prestamos; }
        public java.math.BigDecimal getIGSS() { return IGSS; }
        public java.math.BigDecimal getDeducciones() { return Deducciones; }
        public java.math.BigDecimal getTotalPagar() { return TotalPagar; }
    
        // Setters
        public void setIdEmpleado(int idEmpleado) { IdEmpleado = idEmpleado; }
        public void setNombre(String nombre) { Nombre = nombre; }
        public void setApellido(String apellido) { Apellido = apellido; }
        public void setDPI(String dpi) { DPI = dpi; }
        public void setFechaIngreso(java.sql.Date fechaIngreso) { FechaIngreso = fechaIngreso; }
        public void setSalarioBase(java.math.BigDecimal salarioBase) { SalarioBase = salarioBase; }
        public void setEstado(String estado) { Estado = estado; }
        public void setIdNomina(int idNomina) { IdNomina = idNomina; }
        public void setFechaPago(java.sql.Date fechaPago) { FechaPago = fechaPago; }
        public void setSalario(java.math.BigDecimal salario) { Salario = salario; }
        public void setHorasExtras(int horasExtras) { HorasExtras = horasExtras; }
        public void setComisiones(java.math.BigDecimal comisiones) { Comisiones = comisiones; }
        public void setBonificaciones(java.math.BigDecimal bonificaciones) { Bonificaciones = bonificaciones; }
        public void setValorHorasExtras(java.math.BigDecimal valorHorasExtras) { ValorHorasExtras = valorHorasExtras; }
        public void setTotalDevengado(java.math.BigDecimal totalDevengado) { TotalDevengado = totalDevengado; }
        public void setISR(java.math.BigDecimal isr) { ISR = isr; }
        public void setAnticipos(java.math.BigDecimal anticipos) { Anticipos = anticipos; }
        public void setJudiciales(java.math.BigDecimal judiciales) { Judiciales = judiciales; }
        public void setPrestamos(java.math.BigDecimal prestamos) { Prestamos = prestamos; }
        public void setIGSS(java.math.BigDecimal igss) { IGSS = igss; }
        public void setDeducciones(java.math.BigDecimal deducciones) { Deducciones = deducciones; }
        public void setTotalPagar(java.math.BigDecimal totalPagar) { TotalPagar = totalPagar; }
    }
}