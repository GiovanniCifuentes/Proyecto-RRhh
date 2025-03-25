package vista;

import controlador.Usuario;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {

    final private Font mainFont = new Font("Tahoma", Font.BOLD, 16);
    private Usuario user;

    // Ajusta los detalles de conexión para SQL Server
    String url = "jdbc:sqlserver://localhost:1433;databaseName=RRHH;encrypt=true;trustServerCertificate=true";
    String usuario = "sa";  // Usuario de SQL Server
    String contraseña = "1234";

    public Login() {
        initComponents();
        ImageIcon image = new ImageIcon("bin/icons/10612410.png");

        // ***** Configuración de la ventana Login ******
        setTitle("Recursos humanos Empresa S.A.");
        setIconImage(image.getImage());
        setLocationRelativeTo(null);
        setSize(600, 400);
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1, 10, 10));
        setVisible(true);
    }

    private void initComponents() {
        JLabel labelTitulo = new JLabel("Seguridad del Sistema", SwingConstants.CENTER);
        labelTitulo.setFont(new java.awt.Font("Tahoma", Font.BOLD, 20));
        JLabel labelUsuario = new JLabel("Usuario", SwingConstants.CENTER);
        labelUsuario.setFont(mainFont);
        JLabel labelContraseña = new JLabel("Contraseña", SwingConstants.CENTER);
        labelContraseña.setFont(mainFont);
        JTextField txtUsuario = new JTextField();
        JPasswordField txtContraseña = new JPasswordField();
        txtContraseña.setFont(mainFont);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(mainFont);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(0, 1, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        loginPanel.add(labelTitulo);
        loginPanel.add(labelUsuario);
        loginPanel.add(txtUsuario);
        loginPanel.add(labelContraseña);
        loginPanel.add(txtContraseña);
        loginPanel.add(btnAceptar);

        add(loginPanel);

        // Evento de clic en el botón "Aceptar"
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String usuario = txtUsuario.getText().trim(); // Eliminar espacios extra
                String contraseña = new String(txtContraseña.getPassword()).trim(); // Eliminar espacios extra
                if (authenticateUser(usuario, contraseña)) {
                    JOptionPane.showMessageDialog(null, "Logueado con éxito!");
                    General menuGeneral = new General(); // Mostrar el menú general
                    dispose(); // Cerrar la ventana de login
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña inválidos. Intente de nuevo.");
                }
            }
        });
    }

    // Método para autenticar al usuario en la base de datos
    private boolean authenticateUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(url, usuario, contraseña)) {
            String sql = "SELECT * FROM Usuarios WHERE NombreUsuario = ? AND ContraseñaUsuario = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Usuario encontrado: " + rs.getString("NombreUsuario")); // Debugging
                return true; // Si hay un resultado, el usuario es válido
            } else {
                System.out.println("Usuario no encontrado"); // Debugging
                return false; // Si no hay resultados, usuario no válido
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos: " + e.getMessage());
            return false; // En caso de error, retornar false
        }
    }
}
