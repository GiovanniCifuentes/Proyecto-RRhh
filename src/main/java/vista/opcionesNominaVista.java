package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class opcionesNominaVista extends JInternalFrame {

    private JTextField txtIdNomina;
    private JCheckBox checkSemanal, checkQuincenal, checkMensual;
    private JButton btnBuscar;
    private JButton btnCerrar; // Botón para cerrar la ventana
    private JPanel panelResultados; // Panel para mostrar los resultados

    public opcionesNominaVista() {
        // Configuración de la ventana
        setTitle("Consulta de Nómina");
        setSize(1500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de entrada de datos
        JPanel panelEntrada = new JPanel(new GridLayout(5, 2, 10, 10)); // Aumentar a 5 filas para el botón "Cerrar"
        panelEntrada.setBorder(BorderFactory.createTitledBorder("Filtros de Búsqueda"));

        // Campo para ingresar el ID de la nómina
        panelEntrada.add(new JLabel("ID de Nómina:"));
        txtIdNomina = new JTextField();
        panelEntrada.add(txtIdNomina);

        // Checkboxes para seleccionar el período
        panelEntrada.add(new JLabel("Período:"));
        JPanel panelCheckboxes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkSemanal = new JCheckBox("Semanal");
        checkQuincenal = new JCheckBox("Quincenal");
        checkMensual = new JCheckBox("Mensual");
        panelCheckboxes.add(checkSemanal);
        panelCheckboxes.add(checkQuincenal);
        panelCheckboxes.add(checkMensual);
        panelEntrada.add(panelCheckboxes);

        // Botón para buscar
        btnBuscar = new JButton("Buscar");
        panelEntrada.add(btnBuscar);

        // Botón para cerrar la ventana
        btnCerrar = new JButton("Cerrar");
        panelEntrada.add(new JLabel()); // Espacio vacío para alinear el botón
        panelEntrada.add(btnCerrar);

        // Configurar la acción del botón "Cerrar"
        btnCerrar.addActionListener(e -> dispose()); // Cierra la ventana actual

        // Agregar el panel de entrada al panel principal
        panelPrincipal.add(panelEntrada, BorderLayout.NORTH);

        // Panel para mostrar los resultados
        panelResultados = new JPanel();
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS)); // Alinear en vertical
        JScrollPane scrollResultados = new JScrollPane(panelResultados);
        panelPrincipal.add(scrollResultados, BorderLayout.CENTER);

        // Agregar el panel principal a la ventana
        add(panelPrincipal);
    }

    // Método para obtener el ID de la nómina
    public int getIdNomina() {
        return Integer.parseInt(txtIdNomina.getText());
    }

    // Método para obtener el período seleccionado
    public String getPeriodoSeleccionado() {
        if (checkSemanal.isSelected()) return "SEMANAL";
        if (checkQuincenal.isSelected()) return "QUINCENAL";
        if (checkMensual.isSelected()) return "MENSUAL";
        return "";
    }

    // Método para mostrar los resultados
    public void mostrarResultados(List<List<String>> resultados, String[][] nombresColumnas) {
        // Limpiar el panel de resultados
        panelResultados.removeAll();

        // Títulos de las tablas
        String[] titulos = {
            "Información del empleado",
            "Nómina del empleado",
            "Total a pagar o Salario Total"
        };

        // Crear una tabla para cada conjunto de resultados
        for (int i = 0; i < resultados.size(); i++) {
            DefaultTableModel modeloTabla = new DefaultTableModel();
            JTable tabla = new JTable(modeloTabla);
            JScrollPane scrollPane = new JScrollPane(tabla);

            // Configurar las columnas de la tabla
            for (String nombreColumna : nombresColumnas[i]) {
                modeloTabla.addColumn(nombreColumna);
            }

            // Agregar solo la fila correspondiente a esta tabla
            if (i < resultados.size()) { // Verificar que haya suficientes filas
                List<String> fila = resultados.get(i); // Obtener la fila correspondiente
                Object[] filaDatos = fila.toArray(); // Convertir la fila a un arreglo de objetos
                modeloTabla.addRow(filaDatos); // Agregar la fila a la tabla
            }

            // Agregar un título específico para cada tabla
            JLabel tituloTabla = new JLabel(titulos[i]); // Usar el título del arreglo
            tituloTabla.setFont(new Font("Arial", Font.BOLD, 14));
            tituloTabla.setAlignmentX(JLabel.LEFT_ALIGNMENT); // Alinear el título a la izquierda
            panelResultados.add(tituloTabla);

            // Configurar el JScrollPane para que no ocupe más espacio del necesario
            scrollPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
            panelResultados.add(scrollPane);
        }

        // Reducir el espacio entre los componentes
        panelResultados.setBorder(BorderFactory.createEmptyBorder(3, 3, 5, 5)); // Márgenes reducidos

        // Actualizar la interfaz
        panelResultados.revalidate();
        panelResultados.repaint();
    }

    // Método para mostrar mensajes en la vista
    public void mostrarMensaje(String mensaje, String titulo, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipoMensaje);
    }

    // Método para configurar el listener del botón de búsqueda
    public void setBuscarListener(ActionListener listener) {
        btnBuscar.addActionListener(listener);
    }
}