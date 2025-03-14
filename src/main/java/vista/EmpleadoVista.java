package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class EmpleadoVista extends JFrame {
    public JTextField txtId, txtNombre, txtPuesto, txtSalario;
    public JButton btnAgregar, btnModificar, btnEliminar, btnMostrar;
    public JTable tablaEmpleados;
    public DefaultTableModel modeloTabla;

    public EmpleadoVista() {
        setTitle("Gestión de Empleados");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 📌 Panel de entrada de datos
        JPanel panelEntrada = new JPanel(new GridLayout(4, 2, 5, 5));
        panelEntrada.setBorder(BorderFactory.createTitledBorder("Datos del Empleado"));

        panelEntrada.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelEntrada.add(txtId);

        panelEntrada.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelEntrada.add(txtNombre);

        panelEntrada.add(new JLabel("Puesto:"));
        txtPuesto = new JTextField();
        panelEntrada.add(txtPuesto);

        panelEntrada.add(new JLabel("Salario:"));
        txtSalario = new JTextField();
        panelEntrada.add(txtSalario);

        add(panelEntrada, BorderLayout.NORTH);

        // 📌 Tabla de empleados
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Puesto", "Salario"}, 0);
        tablaEmpleados = new JTable(modeloTabla);
        add(new JScrollPane(tablaEmpleados), BorderLayout.CENTER);

        // 📌 Panel de botones
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnMostrar = new JButton("Mostrar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrar);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }
}
