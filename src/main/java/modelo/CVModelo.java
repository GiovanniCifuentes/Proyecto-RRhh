package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CVModelo {
    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=RRHH;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String CONTRASEÑA = "1234";

    // Método para conectar con la base de datos
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }


    // Método para listar documentos
    public ArrayList<DocumentoPDFVO> listarDocumentos() {
        ArrayList<DocumentoPDFVO> lista = new ArrayList<>();
        String sql = "SELECT IdPDF, nombrePDF, archivoPDF FROM DocumentosPDF;";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DocumentoPDFVO vo = new DocumentoPDFVO();
                vo.setIdPDF(rs.getInt("IdPDF"));
                vo.setNombrePDF(rs.getString("nombrePDF"));
                vo.setArchivoPDF(rs.getBytes("archivoPDF"));
                lista.add(vo);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar documentos: " + ex.getMessage());
        }
        return lista;
    }

    // Método para agregar un documento
    public void agregarDocumento(DocumentoPDFVO vo) {
        String sql = "INSERT INTO DocumentosPDF (nombrePDF, archivoPDF) VALUES (?, ?);";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vo.getNombrePDF());
            ps.setBytes(2, vo.getArchivoPDF());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al agregar documento: " + ex.getMessage());
        }
    }

    // Método para modificar un documento
    public void modificarDocumento(DocumentoPDFVO vo) {
        Connection conn = null;
        try {
            conn = conectar();
            conn.setAutoCommit(false); // Deshabilitar el auto-commit
    
            String sql = "UPDATE DocumentosPDF SET nombrePDF = ?, archivoPDF = ? WHERE IdPDF = ?;";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, vo.getNombrePDF());
                ps.setBytes(2, vo.getArchivoPDF());
                ps.setInt(3, vo.getIdPDF());
                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Documento actualizado correctamente.");
                } else {
                    System.out.println("No se encontró el documento con ID: " + vo.getIdPDF());
                }
            }
    
            conn.commit(); // Confirmar la transacción
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir la transacción en caso de error
                } catch (SQLException e) {
                    System.out.println("Error al revertir la transacción: " + e.getMessage());
                }
            }
            System.out.println("Error al modificar documento: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restaurar el auto-commit
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }

    // Método para eliminar un documento
    public void eliminarDocumento(int idPDF) {
        String sql = "DELETE FROM DocumentosPDF WHERE IdPDF = ?;";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPDF);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar documento: " + ex.getMessage());
        }
    }

    // Método para obtener un documento por su ID
    public DocumentoPDFVO obtenerDocumentoPorId(int idPDF) {
        String sql = "SELECT IdPDF, nombrePDF, archivoPDF FROM DocumentosPDF WHERE IdPDF = ?;";
        DocumentoPDFVO vo = null;
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPDF);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vo = new DocumentoPDFVO();
                    vo.setIdPDF(rs.getInt("IdPDF"));
                    vo.setNombrePDF(rs.getString("nombrePDF"));
                    vo.setArchivoPDF(rs.getBytes("archivoPDF"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener documento: " + ex.getMessage());
        }
        return vo;
    }
    
    public byte[] obtenerArchivoPDF(int idPDF) {
        String sql = "SELECT archivoPDF FROM DocumentosPDF WHERE IdPDF = ?;";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPDF);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("archivoPDF");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener el archivo PDF: " + ex.getMessage());
        }
        return null;
    }
}