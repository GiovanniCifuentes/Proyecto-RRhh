package controlador;

import java.util.ArrayList;
import modelo.CVModelo;
import modelo.DocumentoPDFVO;
import vista.CVVista;

public class CVControlador {
    private CVModelo modelo;
    private CVVista vista;

    // Constructor que acepta CVModelo y CVVista
    public CVControlador(CVModelo modelo, CVVista vista) {
        this.modelo = modelo;
        this.vista = vista;
        // Aquí puedes inicializar eventos y configuraciones
    }

    // Método para listar documentos
    public ArrayList<DocumentoPDFVO> listarDocumentos() {
        return modelo.listarDocumentos();
    }

    // Método para agregar un documento
    public void agregarDocumento(String nombrePDF, byte[] archivoPDF) {
        DocumentoPDFVO vo = new DocumentoPDFVO();
        vo.setNombrePDF(nombrePDF);
        vo.setArchivoPDF(archivoPDF);
        modelo.agregarDocumento(vo);
    }

    // Método para modificar un documento
    public void modificarDocumento(int idPDF, String nombrePDF, byte[] archivoPDF) {
        DocumentoPDFVO vo = new DocumentoPDFVO();
        vo.setIdPDF(idPDF);
        vo.setNombrePDF(nombrePDF);
        vo.setArchivoPDF(archivoPDF);
        modelo.modificarDocumento(vo);
    }

    // Método para eliminar un documento
    public void eliminarDocumento(int idPDF) {
        modelo.eliminarDocumento(idPDF);
    }

    public byte[] obtenerArchivoPDF(int idPDF) {
        return modelo.obtenerArchivoPDF(idPDF);
    }
}