package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class calculoNominaModelo {

    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=RRHH;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String CONTRASEÑA = "1234";

    // Método para conectar con la base de datos
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

    // Método para crear una nómina utilizando el procedimiento almacenado
    public void crearNomina(NominaVO nomina) {
        // Consulta para obtener el salario base del empleado
        String consultaSalario = "SELECT SalarioBase FROM Empleados WHERE IdEmpleado = ?";
        String sql = "{call CrearNomina(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    
        try (Connection conn = conectar();
             PreparedStatement psConsulta = conn.prepareStatement(consultaSalario);
             PreparedStatement ps = conn.prepareStatement(sql)) {
    
            // Obtener el salario base del empleado
            psConsulta.setInt(1, nomina.getIdEmpleado());
            ResultSet rs = psConsulta.executeQuery();
    
            if (rs.next()) {
                // Verificar si el salario base es NULL
                double salarioBase = rs.getDouble("SalarioBase");
                if (rs.wasNull()) {
                    // Si es NULL, se asigna NULL al salario en la nómina
                    ps.setNull(3, java.sql.Types.DECIMAL); // Salario
                } else {
                    // Si no es NULL, se asigna el salario base a la nómina
                    ps.setDouble(3, salarioBase);
                }
            } else {
                throw new SQLException("Empleado no encontrado.");
            }
    
            // Llamar al procedimiento almacenado para crear la nómina
            ps.setInt(1, nomina.getIdEmpleado());
            ps.setDate(2, new java.sql.Date(nomina.getFechaPago().getTime()));
    
            // Manejo de valores null o vacíos en campos numéricos
            if (nomina.getHorasExtras() == null) {
                ps.setNull(4, java.sql.Types.INTEGER); // Horas Extras
            } else {
                ps.setInt(4, nomina.getHorasExtras());
            }
    
            if (nomina.getComisiones() == null) {
                ps.setNull(5, java.sql.Types.DECIMAL); // Comisiones
            } else {
                ps.setDouble(5, nomina.getComisiones());
            }
    
            if (nomina.getBonificaciones() == null) {
                ps.setNull(6, java.sql.Types.DECIMAL); // Bonificaciones
            } else {
                ps.setDouble(6, nomina.getBonificaciones());
            }
    
            if (nomina.getIsr() == null) {
                ps.setNull(7, java.sql.Types.DECIMAL); // ISR
            } else {
                ps.setDouble(7, nomina.getIsr());
            }
    
            if (nomina.getAnticipos() == null) {
                ps.setNull(8, java.sql.Types.DECIMAL); // Anticipos
            } else {
                ps.setDouble(8, nomina.getAnticipos());
            }
    
            if (nomina.getJudiciales() == null) {
                ps.setNull(9, java.sql.Types.DECIMAL); // Judiciales
            } else {
                ps.setDouble(9, nomina.getJudiciales());
            }
    
            if (nomina.getPrestamos() == null) {
                ps.setNull(10, java.sql.Types.DECIMAL); // Préstamos
            } else {
                ps.setDouble(10, nomina.getPrestamos());
            }
    
            ps.executeUpdate();
            System.out.println("Nómina creada exitosamente.");
        } catch (SQLException ex) {
            System.out.println("Error al crear nómina: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Método para leer nóminas utilizando el procedimiento almacenado
    public ArrayList<NominaVO> leerNominas(Integer idNomina, Integer idEmpleado) {
        ArrayList<NominaVO> lista = new ArrayList<>();
        String sql = "{call LeerNomina(?, ?)}";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, idNomina);
            ps.setObject(2, idEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NominaVO nomina = new NominaVO();
                    nomina.setIdNomina(rs.getInt("IdNomina"));
                    nomina.setIdEmpleado(rs.getInt("IdEmpleado"));
                    nomina.setFechaPago(rs.getDate("FechaPago"));
                    nomina.setSalario(rs.getDouble("Salario"));
                    nomina.setHorasExtras(rs.getInt("HorasExtras"));
                    nomina.setComisiones(rs.getDouble("Comisiones"));
                    nomina.setBonificaciones(rs.getDouble("Bonificaciones"));
                    nomina.setValorHorasExtras(rs.getDouble("ValorHorasExtras"));
                    nomina.setTotalDevengado(rs.getDouble("TotalDevengado"));
                    nomina.setIgss(rs.getDouble("IGSS"));
                    nomina.setIsr(rs.getDouble("ISR"));
                    nomina.setAnticipos(rs.getDouble("Anticipos"));
                    nomina.setJudiciales(rs.getDouble("Judiciales"));
                    nomina.setPrestamos(rs.getDouble("Prestamos"));
                    nomina.setDeducciones(rs.getDouble("Deducciones"));
                    nomina.setTotalPagar(rs.getDouble("TotalPagar"));
                    lista.add(nomina);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al leer nóminas: " + ex.getMessage());
        }
        return lista;
    }

    // Método para actualizar una nómina utilizando el procedimiento almacenado
    public void actualizarNomina(NominaVO nomina) {
        String sql = "{call ActualizarNomina(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nomina.getIdNomina());
            ps.setObject(2, nomina.getIdEmpleado());
            ps.setObject(3, new java.sql.Date(nomina.getFechaPago().getTime()));
            ps.setObject(4, nomina.getSalario());
            ps.setObject(5, nomina.getHorasExtras());
            ps.setObject(6, nomina.getComisiones());
            ps.setObject(7, nomina.getBonificaciones());
            ps.setObject(8, nomina.getIsr());
            ps.setObject(9, nomina.getAnticipos());
            ps.setObject(10, nomina.getJudiciales());
            ps.setObject(11, nomina.getPrestamos());
            ps.executeUpdate();
            System.out.println("Nómina actualizada exitosamente.");
        } catch (SQLException ex) {
            System.out.println("Error al actualizar nómina: " + ex.getMessage());
        }
    }

    // Método para eliminar una nómina utilizando el procedimiento almacenado
    public void eliminarNomina(int idNomina) {
        String sql = "{call EliminarNomina(?)}";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idNomina);
            ps.executeUpdate();
            System.out.println("Nómina eliminada exitosamente.");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar nómina: " + ex.getMessage());
        }
    }

    // Método para obtener una nómina por su ID utilizando el procedimiento almacenado
    public NominaVO obtenerNominaPorId(int idNomina) {
        String sql = "{call LeerNomina(?, NULL)}";
        NominaVO nomina = null;
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idNomina);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nomina = new NominaVO();
                    nomina.setIdNomina(rs.getInt("IdNomina"));
                    nomina.setIdEmpleado(rs.getInt("IdEmpleado"));
                    nomina.setFechaPago(rs.getDate("FechaPago"));
                    nomina.setSalario(rs.getDouble("Salario"));
                    nomina.setHorasExtras(rs.getInt("HorasExtras"));
                    nomina.setComisiones(rs.getDouble("Comisiones"));
                    nomina.setBonificaciones(rs.getDouble("Bonificaciones"));
                    nomina.setValorHorasExtras(rs.getDouble("ValorHorasExtras"));
                    nomina.setTotalDevengado(rs.getDouble("TotalDevengado"));
                    nomina.setIgss(rs.getDouble("IGSS"));
                    nomina.setIsr(rs.getDouble("ISR"));
                    nomina.setAnticipos(rs.getDouble("Anticipos"));
                    nomina.setJudiciales(rs.getDouble("Judiciales"));
                    nomina.setPrestamos(rs.getDouble("Prestamos"));
                    nomina.setDeducciones(rs.getDouble("Deducciones"));
                    nomina.setTotalPagar(rs.getDouble("TotalPagar"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener nómina: " + ex.getMessage());
        }
        return nomina;
    }
}