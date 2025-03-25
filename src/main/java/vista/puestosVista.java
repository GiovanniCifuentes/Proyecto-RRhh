package vista;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class puestosVista extends JFrame {
    public JTextField txtIdRol, txtPuesto, txtSalarioPuesto;
    public JButton btnAgregar, btnModificar, btnEliminar, btnBuscar, btnLimpiar;
    public JTable tablaPuestos;
    public DefaultTableModel modeloTabla;

    public puestosVista() {
        ImageIcon image = new ImageIcon("bin/icons/10612410.png");
        setTitle("Recursos humanos Empresa S.A.");
        setIconImage(image.getImage());//Ponemos una imagen como icono de la ventana

        setTitle("Gestión de Puestos");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

            // 📌 Panel de entrada de datos
        JPanel panelEntrada = new JPanel(new GridLayout(7, 2, 5, 5));
        panelEntrada.setBorder(BorderFactory.createTitledBorder("Datos del Puesto"));

        panelEntrada.add(new JLabel("ID Rol:"));
        txtIdRol = new JTextField();
        panelEntrada.add(txtIdRol);

        panelEntrada.add(new JLabel("Puesto:"));
        txtPuesto = new JTextField();
        panelEntrada.add(txtPuesto);

        panelEntrada.add(new JLabel("Salario Puesto:"));
        txtSalarioPuesto = new JTextField();
        panelEntrada.add(txtSalarioPuesto);


        add(panelEntrada, BorderLayout.NORTH);

        // 📌 Tabla de empleados
        modeloTabla = new DefaultTableModel(new String[]{"IdRol", "NombreRol", "SalarioBase"}, 0);
        tablaPuestos = new JTable(modeloTabla);
        add(new JScrollPane(tablaPuestos), BorderLayout.CENTER);

        // 📌 Panel de botones
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Método para obtener el modelo de la tabla
    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
}
