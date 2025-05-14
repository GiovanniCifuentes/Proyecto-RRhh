package vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VistaPrestaciones extends JFrame {
    JTable tabla;
    JButton btnActualizar, btnDescontarVacaciones, btnGenerarPDF;

    public VistaPrestaciones() {
        setTitle("Gestión de Prestaciones");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLocationRelativeTo(null);

        tabla = new JTable();
        btnActualizar = new JButton("Actualizar Datos");
        btnDescontarVacaciones = new JButton("Descontar Vacaciones");
        btnGenerarPDF = new JButton("Generar PDF");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnActualizar);
        panelBotones.add(btnDescontarVacaciones);
        panelBotones.add(btnGenerarPDF);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }

    public void actualizarTabla(DefaultTableModel modelo) {
        tabla.setModel(modelo);
    }
    
    public JButton getBtnActualizar() {
        return btnActualizar;
    }
    
    public JButton getBtnDescontarVacaciones() {
        return btnDescontarVacaciones;
    }
    
    public JButton getBtnGenerarPDF() {
        return btnGenerarPDF;
    }
    
    public int getIdEmpleadoSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            return Integer.parseInt(tabla.getValueAt(filaSeleccionada, 1).toString()); // Columna 1 es IdEmpleado
        }
        return -1;
    }
}
