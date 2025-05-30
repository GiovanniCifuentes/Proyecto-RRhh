package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class empleadoGestionVista extends JFrame {
    public JTextField txtID, txtNombre, txtApellido, txtDPI, txtSalarioBase, txtIdRol;
    public JButton btnModificar, btnEliminar, btnBuscar, btnLimpiar, btnActivar;
    public JTable tablaEmpleados;
    public DefaultTableModel modeloTabla;
    public JFormattedTextField txtFechaIngreso;
    public JCheckBox chkMostrarInactivos;
    public JButton btnMostrarInactivos;

    public empleadoGestionVista() {
        ImageIcon image = new ImageIcon("bin/icons/10612410.png");
        setTitle("Recursos humanos Empresa S.A.");
        setIconImage(image.getImage());//Ponemos una imagen como icono de la ventana

        setTitle("Gestión de Empleados");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

            // 📌 Panel de entrada de datos
        JPanel panelEntrada = new JPanel(new GridLayout(7, 2, 5, 5));
        panelEntrada.setBorder(BorderFactory.createTitledBorder("Datos del Empleado"));

        panelEntrada.add(new JLabel("ID Empleado:"));
        txtID = new JTextField();
        //txtID.setEditable(false); 
        panelEntrada.add(txtID);

        panelEntrada.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelEntrada.add(txtNombre);

        panelEntrada.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelEntrada.add(txtApellido);

        panelEntrada.add(new JLabel("DPI:"));
        txtDPI = new JTextField();
        panelEntrada.add(txtDPI);

        panelEntrada.add(new JLabel("Fecha de Ingreso (YYYY-MM-DD):"));
        txtFechaIngreso = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        txtFechaIngreso.setValue(new Date());
        panelEntrada.add(txtFechaIngreso);

        panelEntrada.add(new JLabel("Salario Base:"));
        txtSalarioBase = new JTextField();
        panelEntrada.add(txtSalarioBase);

        panelEntrada.add(new JLabel("ID Rol:"));
        txtIdRol = new JTextField();
        panelEntrada.add(txtIdRol);

        add(panelEntrada, BorderLayout.NORTH);

        // 📌 Tabla de empleados
         modeloTabla = new DefaultTableModel(new String[]{
            "ID", "Nombre", "Apellido", "DPI", "FechaIngreso", 
            "SalarioBase", "IdRol", "Estado"}, 0);
        tablaEmpleados = new JTable(modeloTabla);
        add(new JScrollPane(tablaEmpleados), BorderLayout.CENTER);

        /*JPanel panelFiltros = new JPanel();
        chkMostrarInactivos = new JCheckBox("Mostrar empleados inactivos");
        btnMostrarInactivos = new JButton("Aplicar Filtro");
        panelFiltros.add(chkMostrarInactivos);
        panelFiltros.add(btnMostrarInactivos);
        add(panelFiltros, BorderLayout.NORTH);*/

        // 📌 Panel de botones
        JPanel panelBotones = new JPanel();
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnActivar = new JButton("Activar");
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActivar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

     public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
    
    public boolean mostrarInactivos() {
        return chkMostrarInactivos.isSelected();
    }

}
