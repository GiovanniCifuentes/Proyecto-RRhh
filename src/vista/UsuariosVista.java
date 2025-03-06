package vista;

import java.awt.*;
import javax.swing.*;
import modelo.UsuariosModelo;

public class UsuariosVista extends JFrame {
    private UsuariosModelo modelo;

    // Componentes de la interfaz
    private JTextField txtIdUsuario, txtNombreUsuario, txtContraseñaUsuario, txtNuevaContraseñaUsuario, txtContraseñaActual;
    private JButton btnInsertar, btnModificar, btnEliminar;

    public UsuariosVista() {
        this.modelo = new UsuariosModelo(); // Instancia del modelo

        setTitle("Gestión de Usuarios");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de entrada de datos
        JPanel panelEntrada = new JPanel(new GridLayout(6, 2, 5, 5));
        panelEntrada.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));

        // Campos de texto
        panelEntrada.add(new JLabel("ID Usuario (solo para modificar/eliminar):"));
        txtIdUsuario = new JTextField();
        panelEntrada.add(txtIdUsuario);

        panelEntrada.add(new JLabel("Nombre de Usuario:"));
        txtNombreUsuario = new JTextField();
        panelEntrada.add(txtNombreUsuario);

        panelEntrada.add(new JLabel("Contraseña:"));
        txtContraseñaUsuario = new JTextField();
        panelEntrada.add(txtContraseñaUsuario);

        panelEntrada.add(new JLabel("Nueva Contraseña (solo para modificar):"));
        txtNuevaContraseñaUsuario = new JTextField();
        panelEntrada.add(txtNuevaContraseñaUsuario);

        panelEntrada.add(new JLabel("Contraseña Actual (solo para modificar/eliminar):"));
        txtContraseñaActual = new JTextField();
        panelEntrada.add(txtContraseñaActual);

        add(panelEntrada, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnInsertar = new JButton("Insertar Usuario");
        btnModificar = new JButton("Modificar Usuario");
        btnEliminar = new JButton("Eliminar Usuario");

        panelBotones.add(btnInsertar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    public JButton getBtnInsertar() {
        return btnInsertar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JTextField getTxtIdUsuario() {
        return txtIdUsuario;
    }

    public JTextField getTxtNombreUsuario() {
        return txtNombreUsuario;
    }

    public JTextField getTxtContraseñaUsuario() {
        return txtContraseñaUsuario;
    }

    public JTextField getTxtNuevaContraseñaUsuario() {
        return txtNuevaContraseñaUsuario;
    }

    public JTextField getTxtContraseñaActual() {
        return txtContraseñaActual;
    }

    // Método para mostrar mensajes
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Método para limpiar los campos del formulario
    public void limpiarCampos() {
        txtIdUsuario.setText("");
        txtNombreUsuario.setText("");
        txtContraseñaUsuario.setText("");
        txtNuevaContraseñaUsuario.setText("");
        txtContraseñaActual.setText("");
    }


}
