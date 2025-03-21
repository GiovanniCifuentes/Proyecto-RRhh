package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import modelo.NominaVO;
import modelo.calculoNominaModelo;

public class calculoNominaVista extends JInternalFrame {
    private calculoNominaModelo modelo;
    private JTable tablaNominas;
    private DefaultTableModel modeloTabla;
    private JTextField txtIdNomina, txtIdEmpleado, txtFechaPago, txtSalario, txtHorasExtras, txtComisiones, txtBonificaciones, txtISR, txtAnticipos, txtJudiciales, txtPrestamos;
    private JButton btnCrear, btnActualizar, btnEliminar, btnBuscar, btnLimpiar, cerrarButton;
    private JTextField txtTotalDeducciones, txtTotalIGSS, txtTotalPagar;

    public calculoNominaVista() {
        // Configuración de la ventana
        setTitle("Gestión de Nóminas");
        setSize(1500, 950);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Inicializar el modelo
        modelo = new calculoNominaModelo();

        // Crear componentes
        crearComponentes();

        // Organizar componentes en la ventana
        organizarComponentes();

        // Cargar datos iniciales en la tabla
        cargarNominas();
    }

    private void crearComponentes() {
        // Campos de texto existentes
        txtIdNomina = new JTextField(10);
        txtIdEmpleado = new JTextField(10);
        txtFechaPago = new JTextField(10);
        txtSalario = new JTextField(10);
        txtSalario.setEditable(false);
        txtHorasExtras = new JTextField(10);
        txtComisiones = new JTextField(10);
        txtBonificaciones = new JTextField(10);
        txtISR = new JTextField(10);
        txtAnticipos = new JTextField(10);
        txtJudiciales = new JTextField(10);
        txtPrestamos = new JTextField(10);

        // Nuevos campos para Total de Deducciones y Total del IGSS
        txtTotalDeducciones = new JTextField(10);
        txtTotalDeducciones.setEditable(false);
        txtTotalIGSS = new JTextField(10);
        txtTotalIGSS.setEditable(false);

        // Botones
        btnCrear = new JButton("Crear Nómina");
        btnActualizar = new JButton("Actualizar Nómina");
        btnEliminar = new JButton("Eliminar Nómina");
        btnBuscar = new JButton("Buscar por ID");
        btnLimpiar = new JButton("Limpiar Campos");
        cerrarButton = new JButton("Cerrar");

        // Acción para el botón Limpiar
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        // Acción para el botón Cerrar
        cerrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana
            }
        });

        // Tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID Nómina");
        modeloTabla.addColumn("ID Empleado");
        modeloTabla.addColumn("Fecha Pago");
        modeloTabla.addColumn("Salario");
        modeloTabla.addColumn("Horas Extras");
        modeloTabla.addColumn("Comisiones");
        modeloTabla.addColumn("Bonificaciones");
        modeloTabla.addColumn("ISR");
        modeloTabla.addColumn("Anticipos");
        modeloTabla.addColumn("Judiciales");
        modeloTabla.addColumn("Préstamos");
        modeloTabla.addColumn("Total Deducciones");
        modeloTabla.addColumn("Total IGSS");
        modeloTabla.addColumn("Total a Pagar");

        tablaNominas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaNominas);
    }

    public JButton getBtnCrear() {
        return btnCrear;
    }
    
    public JButton getBtnActualizar() {
        return btnActualizar;
    }
    
    public JButton getBtnEliminar() {
        return btnEliminar;
    }
    
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    private void organizarComponentes() {
        // Panel para los campos de entrada
        JPanel panelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Agregar campos de entrada al panel
        agregarCampo(panelEntrada, gbc, "ID Nómina:", txtIdNomina, 0);
        agregarCampo(panelEntrada, gbc, "ID Empleado:", txtIdEmpleado, 1);
        agregarCampo(panelEntrada, gbc, "Fecha Pago (yyyy-MM-dd):", txtFechaPago, 2);
        agregarCampo(panelEntrada, gbc, "Salario:", txtSalario, 3);
        agregarCampo(panelEntrada, gbc, "Horas Extras:", txtHorasExtras, 4);
        agregarCampo(panelEntrada, gbc, "Comisiones:", txtComisiones, 5);
        agregarCampo(panelEntrada, gbc, "Bonificaciones:", txtBonificaciones, 6);
        agregarCampo(panelEntrada, gbc, "ISR:", txtISR, 7);
        agregarCampo(panelEntrada, gbc, "Anticipos:", txtAnticipos, 8);
        agregarCampo(panelEntrada, gbc, "Judiciales:", txtJudiciales, 9);
        agregarCampo(panelEntrada, gbc, "Préstamos:", txtPrestamos, 10);
        agregarCampo(panelEntrada, gbc, "Total Deducciones:", txtTotalDeducciones, 11);
        agregarCampo(panelEntrada, gbc, "Total IGSS:", txtTotalIGSS, 12);

        // Panel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(cerrarButton);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(panelEntrada, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(tablaNominas), BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Agregar panel principal a la ventana
        add(panelPrincipal);
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, String etiqueta, JTextField campo, int fila) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel(etiqueta), gbc);

        gbc.gridx = 1;
        gbc.gridy = fila;
        panel.add(campo, gbc);
    }

    private void limpiarCampos() {
        txtIdNomina.setText("");
        txtIdEmpleado.setText("");
        txtFechaPago.setText("");
        txtSalario.setText("");
        txtHorasExtras.setText("");
        txtComisiones.setText("");
        txtBonificaciones.setText("");
        txtISR.setText("");
        txtAnticipos.setText("");
        txtJudiciales.setText("");
        txtPrestamos.setText("");
        txtTotalDeducciones.setText("");
        txtTotalIGSS.setText("");
    }

    private void cargarNominas() {
        // Limpiar la tabla
        modeloTabla.setRowCount(0);
    
        // Obtener todas las nóminas
        ArrayList<NominaVO> nominas = modelo.leerNominas(null, null);
    
        // Llenar la tabla con los datos
        for (NominaVO nomina : nominas) {
            // Calcular totales
            double totalDeducciones = (nomina.getIsr() != null ? nomina.getIsr() : 0) +
                                      (nomina.getAnticipos() != null ? nomina.getAnticipos() : 0) +
                                      (nomina.getJudiciales() != null ? nomina.getJudiciales() : 0) +
                                      (nomina.getPrestamos() != null ? nomina.getPrestamos() : 0);
            double totalIGSS = nomina.getSalario() * 0.0483;
            double totalPagar = nomina.getSalario() +
                               (nomina.getHorasExtras() != null ? nomina.getHorasExtras() : 0) +
                               (nomina.getComisiones() != null ? nomina.getComisiones() : 0) +
                               (nomina.getBonificaciones() != null ? nomina.getBonificaciones() : 0) -
                               totalDeducciones - totalIGSS;
    
            // Agregar fila a la tabla
            Object[] fila = {
                nomina.getIdNomina(),
                nomina.getIdEmpleado(),
                nomina.getFechaPago(),
                nomina.getSalario(),
                nomina.getHorasExtras(),
                nomina.getComisiones(),
                nomina.getBonificaciones(),
                nomina.getIsr(),
                nomina.getAnticipos(),
                nomina.getJudiciales(),
                nomina.getPrestamos(),
                totalDeducciones,
                totalIGSS,
                totalPagar // Agrega el Total a Pagar
            };
            modeloTabla.addRow(fila);
        }
    }

    // Métodos para obtener datos de los campos
    public int getTxtIdNomina() {
        return Integer.parseInt(txtIdNomina.getText());
    }

    public int getTxtIdEmpleado() {
        return Integer.parseInt(txtIdEmpleado.getText());
    }

    public String getTxtFechaPago() {
        return txtFechaPago.getText();
    }

    public double getTxtSalario() {
        return Double.parseDouble(txtSalario.getText());
    }

    public Integer getTxtHorasExtras() {
        return txtHorasExtras.getText().isEmpty() ? null : Integer.parseInt(txtHorasExtras.getText());
    }

    public Double getTxtComisiones() {
        return txtComisiones.getText().isEmpty() ? null : Double.parseDouble(txtComisiones.getText());
    }

    public Double getTxtBonificaciones() {
        return txtBonificaciones.getText().isEmpty() ? null : Double.parseDouble(txtBonificaciones.getText());
    }

    public Double getTxtISR() {
        return txtISR.getText().isEmpty() ? null : Double.parseDouble(txtISR.getText());
    }

    public Double getTxtAnticipos() {
        return txtAnticipos.getText().isEmpty() ? null : Double.parseDouble(txtAnticipos.getText());
    }

    public Double getTxtJudiciales() {
        return txtJudiciales.getText().isEmpty() ? null : Double.parseDouble(txtJudiciales.getText());
    }

    public Double getTxtPrestamos() {
        return txtPrestamos.getText().isEmpty() ? null : Double.parseDouble(txtPrestamos.getText());
    }

    public void mostrarNomina(NominaVO nomina) {
        txtIdEmpleado.setText(String.valueOf(nomina.getIdEmpleado()));
        txtFechaPago.setText(nomina.getFechaPago().toString());
        txtSalario.setText(String.valueOf(nomina.getSalario()));
        txtHorasExtras.setText(nomina.getHorasExtras() != null ? String.valueOf(nomina.getHorasExtras()) : "");
        txtComisiones.setText(nomina.getComisiones() != null ? String.valueOf(nomina.getComisiones()) : "");
        txtBonificaciones.setText(nomina.getBonificaciones() != null ? String.valueOf(nomina.getBonificaciones()) : "");
        txtISR.setText(nomina.getIsr() != null ? String.valueOf(nomina.getIsr()) : "");
        txtAnticipos.setText(nomina.getAnticipos() != null ? String.valueOf(nomina.getAnticipos()) : "");
        txtJudiciales.setText(nomina.getJudiciales() != null ? String.valueOf(nomina.getJudiciales()) : "");
        txtPrestamos.setText(nomina.getPrestamos() != null ? String.valueOf(nomina.getPrestamos()) : "");
    
        // Calcular y mostrar el Total de Deducciones
        double totalDeducciones = (nomina.getIsr() != null ? nomina.getIsr() : 0) +
                                  (nomina.getAnticipos() != null ? nomina.getAnticipos() : 0) +
                                  (nomina.getJudiciales() != null ? nomina.getJudiciales() : 0) +
                                  (nomina.getPrestamos() != null ? nomina.getPrestamos() : 0);
        txtTotalDeducciones.setText(String.valueOf(totalDeducciones));
    
        // Calcular y mostrar el Total del IGSS
        double totalIGSS = (nomina.getSalario() + nomina.getComisiones() + nomina.getValorHorasExtras())* 0.0483; // 4.83% del salario
        txtTotalIGSS.setText(String.valueOf(totalIGSS));
    
        // Calcular y mostrar el Total a Pagar
        double totalPagar = (nomina.getSalario() +
                            (nomina.getValorHorasExtras() != null ? nomina.getValorHorasExtras() : 0) +
                            (nomina.getComisiones() != null ? nomina.getComisiones() : 0) +
                            (nomina.getBonificaciones() != null ? nomina.getBonificaciones() : 0))-
                            (totalDeducciones - totalIGSS);
        txtTotalPagar.setText(String.valueOf(totalPagar)); // Usa txtTotalPagar
    }

    public void mostrarNominas(ArrayList<NominaVO> nominas) {
        modeloTabla.setRowCount(0);
        for (NominaVO nomina : nominas) {
            Object[] fila = {
                    nomina.getIdNomina(),
                    nomina.getIdEmpleado(),
                    nomina.getFechaPago(),
                    nomina.getSalario(),
                    nomina.getHorasExtras(),
                    nomina.getComisiones(),
                    nomina.getBonificaciones(),
                    nomina.getIsr(),
                    nomina.getAnticipos(),
                    nomina.getJudiciales(),
                    nomina.getPrestamos(),
                    nomina.getTotalPagar()
            };
            modeloTabla.addRow(fila);
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}