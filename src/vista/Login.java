package vista;

import controlador.Usuario;
import modelo.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    final private Font mainFont = new Font("Tahoma", Font.BOLD, 16);// Se crea un formato de texto estandar
    private Usuario user;

    public Login() {
        initComponents();
        ImageIcon image = new ImageIcon("src/icons/10612410.png");

        user = new Usuario("kev","123");//Datos temporales para login

        // ***** Configuracion de la ventana Login ******
        setTitle("Recursos humanos Empresa S.A.");
        setIconImage(image.getImage());//Ponemos una imagen como icono de la ventana
        setLocationRelativeTo(null);
        setSize(600, 400);
        setMinimumSize(new Dimension(600,400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0,1,10,10));
        setVisible(true);
    }


    private void initComponents() {

        // ****** Diseño y estructura de los cuadros de texto ******
        JLabel labelTitulo = new JLabel("Seguridad del Sistema", SwingConstants.CENTER);
        labelTitulo.setFont(new java.awt.Font("Tahoma", 1, 20)); // 
        labelTitulo.setHorizontalAlignment(JLabel.CENTER);
        JLabel labelUsuario = new JLabel("Usuario", SwingConstants.CENTER);
        labelUsuario.setFont(mainFont);
        JLabel labelContraseña = new JLabel("Contraseña", SwingConstants.CENTER);
        labelContraseña.setFont(mainFont);
        JTextField txtUsuario = new JTextField();
        labelContraseña.setFont(mainFont);
        JPasswordField txtContraseña = new JPasswordField();
        txtContraseña.setFont(mainFont);
        
        JButton btnAceptar = new JButton("Aceptar");
        //btnAceptar.setBounds(200, 100, 100, 50);
        btnAceptar.setFont(mainFont);

        //Orden para cada jlabel, cuadro de texto y botones dentro de un JPanel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(0,1,10,10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(40,50,40,50));
        loginPanel.add(labelTitulo);
        loginPanel.add(labelUsuario);
        loginPanel.add(txtUsuario);
        loginPanel.add(labelContraseña);    
        loginPanel.add(txtContraseña);
        loginPanel.add(btnAceptar);

        // Agregar JPanel a la ventana
        add(loginPanel);

        // ******* Apartado para funcionalidad de botones ********
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        user = new Usuario();

        btnAceptar.addActionListener(new ActionListener() { //Funcionalidades del boton Aceptar
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //btnAceptarActionPerformed(evt);
                String usuario = txtUsuario.getText();
                String contraseña = new String(txtContraseña.getPassword());

                if (user.getUsername().equals(usuario) && user.getPassword().equals(contraseña)) {//Si el usuario ingresa los datos correctos podra acceder a la gestion de RRHH
                    JOptionPane.showMessageDialog(null, "Logueado con éxito!");
                    MdiGeneral menuGeneral = new MdiGeneral();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña invalidos. Intente de nuevo.");
                } 
            }
        }); 
        
    }
}