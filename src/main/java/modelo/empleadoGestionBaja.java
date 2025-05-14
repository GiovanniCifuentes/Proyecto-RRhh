package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

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

    public Liquidacion calcularLiquidacion(int idEmpleado) throws SQLException {
        Liquidacion liquidacion = new Liquidacion();
        
        try (Connection con = conectar()) {
            // Obtener datos básicos del empleado
            String sqlEmpleado = "SELECT Nombre, Apellido, FechaIngreso, SalarioBase FROM Empleados WHERE IdEmpleado = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlEmpleado)) {
                ps.setInt(1, idEmpleado);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    liquidacion.setNombre(rs.getString("Nombre"));
                    liquidacion.setApellido(rs.getString("Apellido"));
                    Date fechaIngreso = rs.getDate("FechaIngreso");
                    liquidacion.setFechaIngreso(fechaIngreso.toLocalDate());
                    liquidacion.setSalarioBase(rs.getDouble("SalarioBase"));
                    
                    // Calcular tiempo trabajado
                    Period periodo = Period.between(fechaIngreso.toLocalDate(), LocalDate.now());
                    liquidacion.setAniosTrabajados(periodo.getYears());
                    liquidacion.setMesesTrabajados(periodo.getMonths());
                }
            }
            
            // Obtener salario promedio últimos 6 meses
            String sqlSalarioPromedio = "SELECT AVG(Salario) as SalarioPromedio FROM Nomina " +
                                      "WHERE IdEmpleado = ? AND FechaPago >= DATEADD(MONTH, -6, GETDATE())";
            try (PreparedStatement ps = con.prepareStatement(sqlSalarioPromedio)) {
                ps.setInt(1, idEmpleado);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    liquidacion.setSalarioPromedio(rs.getDouble("SalarioPromedio"));
                } else {
                    // Si no hay nóminas, usar salario base
                    liquidacion.setSalarioPromedio(liquidacion.getSalarioBase());
                }
            }
            
            // Obtener prestaciones actuales
            String sqlPrestaciones = "SELECT DiasVacaciones, Aguinaldo, Bono14 FROM Prestaciones " +
                                    "WHERE IdEmpleado = ? AND CONVERT(DATE, FechaCalculo) = CONVERT(DATE, GETDATE())";
            try (PreparedStatement ps = con.prepareStatement(sqlPrestaciones)) {
                ps.setInt(1, idEmpleado);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    liquidacion.setDiasVacaciones(rs.getDouble("DiasVacaciones"));
                    liquidacion.setAguinaldo(rs.getDouble("Aguinaldo"));
                    liquidacion.setBono14(rs.getDouble("Bono14"));
                }
            }
            
            // Calcular valores de liquidación
            calcularValoresLiquidacion(liquidacion);
        }
        
        return liquidacion;
    }
    
    private void calcularValoresLiquidacion(Liquidacion liquidacion) {
        // 1. Indemnización (1 mes por año trabajado)
        double indemnizacionPorAnio = liquidacion.getSalarioPromedio();
        double indemnizacionProporcional = (indemnizacionPorAnio / 12) * liquidacion.getMesesTrabajados();
        liquidacion.setIndemnizacion((indemnizacionPorAnio * liquidacion.getAniosTrabajados()) + indemnizacionProporcional);
        
        // 2. Vacaciones no gozadas (se pagan los días acumulados)
        double valorDiaVacacion = liquidacion.getSalarioPromedio() / 30; // 30 días = 1 mes
        liquidacion.setVacacionesNoGozadas(liquidacion.getDiasVacaciones() * valorDiaVacacion);
        
        // 3. Aguinaldo proporcional (ya está calculado en prestaciones)
        // 4. Bono 14 proporcional (ya está calculado en prestaciones)
        
        // 5. Prima de antigüedad (12 días por año)
        double primaPorAnio = (liquidacion.getSalarioPromedio() / 30) * 12; // 12 días por año
        double primaProporcional = ((liquidacion.getSalarioPromedio() / 30) / 12) * liquidacion.getMesesTrabajados();
        liquidacion.setPrimaAntiguedad((primaPorAnio * liquidacion.getAniosTrabajados()) + primaProporcional);
        
        // Total liquidación
        liquidacion.setTotalLiquidacion(
            liquidacion.getIndemnizacion() +
            liquidacion.getVacacionesNoGozadas() +
            liquidacion.getAguinaldo() +
            liquidacion.getBono14() +
            liquidacion.getPrimaAntiguedad()
        );
    }

      // Clase interna para manejar los datos de liquidación
    public static class Liquidacion {
        private String nombre;
        private String apellido;
        private LocalDate fechaIngreso;
        private int aniosTrabajados;
        private int mesesTrabajados;
        private double salarioBase;
        private double salarioPromedio;
        private double diasVacaciones;
        private double aguinaldo;
        private double bono14;
        
        // Resultados del cálculo
        private double indemnizacion;
        private double vacacionesNoGozadas;
        private double primaAntiguedad;
        private double totalLiquidacion;

        // Getters y setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getApellido() { return apellido; }
        public void setApellido(String apellido) { this.apellido = apellido; }
        public LocalDate getFechaIngreso() { return fechaIngreso; }
        public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
        public int getAniosTrabajados() { return aniosTrabajados; }
        public void setAniosTrabajados(int aniosTrabajados) { this.aniosTrabajados = aniosTrabajados; }
        public int getMesesTrabajados() { return mesesTrabajados; }
        public void setMesesTrabajados(int mesesTrabajados) { this.mesesTrabajados = mesesTrabajados; }
        public double getSalarioBase() { return salarioBase; }
        public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
        public double getSalarioPromedio() { return salarioPromedio; }
        public void setSalarioPromedio(double salarioPromedio) { this.salarioPromedio = salarioPromedio; }
        public double getDiasVacaciones() { return diasVacaciones; }
        public void setDiasVacaciones(double diasVacaciones) { this.diasVacaciones = diasVacaciones; }
        public double getAguinaldo() { return aguinaldo; }
        public void setAguinaldo(double aguinaldo) { this.aguinaldo = aguinaldo; }
        public double getBono14() { return bono14; }
        public void setBono14(double bono14) { this.bono14 = bono14; }
        public double getIndemnizacion() { return indemnizacion; }
        public void setIndemnizacion(double indemnizacion) { this.indemnizacion = indemnizacion; }
        public double getVacacionesNoGozadas() { return vacacionesNoGozadas; }
        public void setVacacionesNoGozadas(double vacacionesNoGozadas) { this.vacacionesNoGozadas = vacacionesNoGozadas; }
        public double getPrimaAntiguedad() { return primaAntiguedad; }
        public void setPrimaAntiguedad(double primaAntiguedad) { this.primaAntiguedad = primaAntiguedad; }
        public double getTotalLiquidacion() { return totalLiquidacion; }
        public void setTotalLiquidacion(double totalLiquidacion) { this.totalLiquidacion = totalLiquidacion; }
    }

    /*public void eliminar(int idEmpleado) {
        String sql = "{CALL BloquearEmpleado(?)}";
    
        try (Connection conexion = conectar();
                CallableStatement stmt = conexion.prepareCall(sql)) {
    
            stmt.setInt(1, idEmpleado);
    
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Empleado bloqueado/inactivado correctamente. Filas afectadas: " + filasAfectadas);
            } else {
                System.out.println("⚠️ El empleado no existe o ya estaba inactivo");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al bloquear empleado");
            e.printStackTrace();
        }
    }

    public void activar(int idEmpleado) {
        String sql = "{CALL ActivarEmpleado(?)}";
    
        try (Connection conexion = conectar();
                CallableStatement stmt = conexion.prepareCall(sql)) {
    
            stmt.setInt(1, idEmpleado);
    
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Empleado activado correctamente. Filas afectadas: " + filasAfectadas);
            } else {
                System.out.println("⚠️ El empleado no existe o ya estaba activo");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al activar empleado");
            e.printStackTrace();
        }
    }*/

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
