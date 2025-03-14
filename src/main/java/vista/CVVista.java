package vista;

import controlador.CVControlador;
import modelo.CVModelo;
import modelo.DocumentoPDFVO;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.Loader;



import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.io.File;
import java.nio.file.Files;
import java.io.ByteArrayInputStream;
import java.io.IOException;

    
public class CVVista extends JFrame {
    private CVControlador controlador;
    private JTable tablaDocumentos;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombrePDF;
    private JButton btnAgregar, btnModificar, btnEliminar;
    private JButton btnSeleccionarPDF; // Botón para seleccionar archivo PDF
    private byte[] archivoPDF;
    private JButton btnVisualizarPDF; 

    public CVVista() {
    // Configuración de la ventana
    setTitle("Gestión de Documentos PDF");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null); // Centrar la ventana

    // Inicializar el modelo y el controlador
    CVModelo modelo = new CVModelo(); // Crear una instancia de CVModelo
    controlador = new CVControlador(modelo, this); // Pasar modelo y esta vista al controlador

    // Crear componentes
    crearComponentes();

    // Cargar datos iniciales
    cargarDocumentos();
}

    private void crearComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Tabla para mostrar documentos
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre PDF");
        tablaDocumentos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaDocumentos);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Panel de controles
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new FlowLayout());

        txtNombrePDF = new JTextField(20);
        btnSeleccionarPDF = new JButton("Seleccionar PDF");
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnVisualizarPDF = new JButton("Visualizar PDF");

        panelControles.add(new JLabel("Nombre del PDF:"));
        panelControles.add(txtNombrePDF);
        panelControles.add(btnSeleccionarPDF);
        panelControles.add(btnAgregar);
        panelControles.add(btnModificar);
        panelControles.add(btnEliminar);
        panelControles.add(btnVisualizarPDF);

        panelPrincipal.add(panelControles, BorderLayout.SOUTH);

        // Agregar panel principal a la ventana
        add(panelPrincipal);

        // Configurar eventos de los botones
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarDocumento();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarDocumento();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarDocumento();
            }
        });
    

    // Configurar evento para seleccionar archivo PDF
    btnSeleccionarPDF.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            seleccionarPDF();
        }
    });

    btnVisualizarPDF.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            visualizarPDF();
        }
    });

    }

    private void cargarDocumentos() {
        // Limpiar la tabla
        modeloTabla.setRowCount(0);

        // Obtener documentos desde el controlador
        ArrayList<DocumentoPDFVO> documentos = controlador.listarDocumentos();

        // Agregar documentos a la tabla
        for (DocumentoPDFVO doc : documentos) {
            Object[] fila = {doc.getIdPDF(), doc.getNombrePDF()};
            modeloTabla.addRow(fila);
        }
    }

    private void agregarDocumento() {
        String nombrePDF = txtNombrePDF.getText();
        if (!nombrePDF.isEmpty() && archivoPDF != null) {
            // Agregar documento a través del controlador
            controlador.agregarDocumento(nombrePDF, archivoPDF);
    
            // Recargar documentos en la tabla
            cargarDocumentos();
    
            // Limpiar campo de texto y archivo seleccionado
            txtNombrePDF.setText("");
            archivoPDF = null;
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre y seleccionar un archivo PDF.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarDocumento() {
        int filaSeleccionada = tablaDocumentos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idPDF = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            String nombrePDF = txtNombrePDF.getText();

            if (!nombrePDF.isEmpty()) {
                // Simulación de archivo PDF (debes implementar la lectura del archivo)
                byte[] archivoPDF = new byte[0];

                // Modificar documento a través del controlador
                controlador.modificarDocumento(idPDF, nombrePDF, archivoPDF);

                // Recargar documentos en la tabla
                cargarDocumentos();

                // Limpiar campo de texto
                txtNombrePDF.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "El nombre del PDF no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un documento para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarDocumento() {
        int filaSeleccionada = tablaDocumentos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idPDF = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

            // Eliminar documento a través del controlador
            controlador.eliminarDocumento(idPDF);

            // Recargar documentos en la tabla
            cargarDocumentos();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un documento para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void seleccionarPDF() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos PDF", "pdf")); // Filtro para archivos PDF
    int resultado = fileChooser.showOpenDialog(this);

    if (resultado == JFileChooser.APPROVE_OPTION) {
        try {
            // Leer el archivo seleccionado y convertirlo en un arreglo de bytes
            File archivo = fileChooser.getSelectedFile();
            archivoPDF = Files.readAllBytes(archivo.toPath());
            JOptionPane.showMessageDialog(this, "Archivo PDF seleccionado: " + archivo.getName());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo PDF.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
private void visualizarPDF() {
    int filaSeleccionada = tablaDocumentos.getSelectedRow();
    if (filaSeleccionada >= 0) {
        int idPDF = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        // Obtener el archivo PDF desde el controlador
        byte[] archivoPDFBytes = controlador.obtenerArchivoPDF(idPDF); // Cambio de nombre de la variable

        if (archivoPDFBytes != null) {
            try {
                // Cargar el documento PDF directamente desde el arreglo de bytes
                PDDocument document = Loader.loadPDF(archivoPDFBytes);
                PDFRenderer renderer = new PDFRenderer(document);

                // Crear una ventana para mostrar el PDF
                JFrame frame = new JFrame("Visualizar PDF");
                frame.setSize(800, 600);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Usar la clase personalizada PDFPanel
                PDFPanel panel = new PDFPanel(document, renderer);

                // Botones de navegación
                JButton btnAnterior = new JButton("Anterior");
                btnAnterior.addActionListener(e -> panel.mostrarPaginaAnterior());

                JButton btnSiguiente = new JButton("Siguiente");
                btnSiguiente.addActionListener(e -> panel.mostrarPaginaSiguiente());

                // Panel de controles
                JPanel panelControles = new JPanel();
                panelControles.add(btnAnterior);
                panelControles.add(btnSiguiente);

                // Agregar el panel de visualización y controles a la ventana
                frame.add(new JScrollPane(panel), BorderLayout.CENTER);
                frame.add(panelControles, BorderLayout.SOUTH);

                frame.setVisible(true);

                // Cerrar el documento cuando se cierre la ventana
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent windowEvent) {
                        try {
                            document.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al visualizar el PDF.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el archivo PDF.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione un documento para visualizar.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

// Clase personalizada para el panel del PDF
private class PDFPanel extends JPanel {
    private int paginaActual = 0; // Página actual
    private final PDFRenderer renderer;
    private final PDDocument document;

    public PDFPanel(PDDocument document, PDFRenderer renderer) {
        this.document = document;
        this.renderer = renderer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            // Renderizar la página actual
            renderer.renderPageToGraphics(paginaActual, (Graphics2D) g);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void mostrarPaginaAnterior() {
        if (paginaActual > 0) {
            paginaActual--; // Ir a la página anterior
            repaint(); // Volver a renderizar el panel
        }
    }

    public void mostrarPaginaSiguiente() {
        if (paginaActual < document.getNumberOfPages() - 1) {
            paginaActual++; // Ir a la página siguiente
            repaint(); // Volver a renderizar el panel
        }
    }
}



    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CVVista().setVisible(true);
            }
        });
    }
}
