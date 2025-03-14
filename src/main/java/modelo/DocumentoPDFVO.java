package modelo;

public class DocumentoPDFVO {
    private int idPDF;
    private String nombrePDF;
    private byte[] archivoPDF;

    // Constructor vacío
    public DocumentoPDFVO() {}

    // Constructor con parámetros
    public DocumentoPDFVO(int idPDF, String nombrePDF, byte[] archivoPDF) {
        this.idPDF = idPDF;
        this.nombrePDF = nombrePDF;
        this.archivoPDF = archivoPDF;
    }

    // Getters y Setters
    public int getIdPDF() {
        return idPDF;
    }

    public void setIdPDF(int idPDF) {
        this.idPDF = idPDF;
    }

    public String getNombrePDF() {
        return nombrePDF;
    }

    public void setNombrePDF(String nombrePDF) {
        this.nombrePDF = nombrePDF;
    }

    public byte[] getArchivoPDF() {
        return archivoPDF;
    }

    public void setArchivoPDF(byte[] archivoPDF) {
        this.archivoPDF = archivoPDF;
    }
}