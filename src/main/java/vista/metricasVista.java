package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import modelo.metricasModelo.MetricaEmpleado;

public class metricasVista extends JFrame {
    private JComboBox<Integer> cmbAño;
    private JComboBox<Integer> cmbMes;
    private JButton btnGenerarReporte;
    private JButton btnGenerarDatos;
    private JTable tablaMetricas;
    private JPanel panelGraficos;
    private JPanel panelPrincipal;
    private JTextField txtEmpleado;
    private JButton btnVerificarEmpleado;
    private Map<Integer, String> mapaEmpleados;
    private JLabel lblInfoEmpleado;

    public metricasVista() {
        setTitle("Métricas de Horas Trabajadas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        initComponents();
        add(panelPrincipal);
    }

    private void initComponents() {
        panelPrincipal = new JPanel(new BorderLayout());
        
        // Panel de controles
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Panel para selección de empleado
        JPanel panelEmpleado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEmpleado.add(new JLabel("ID Empleado:"));
        
        txtEmpleado = new JTextField(10);
        txtEmpleado.setToolTipText("Ingrese ID o seleccione de la lista");
        panelEmpleado.add(txtEmpleado);
        
        btnVerificarEmpleado = new JButton("Verificar");
        panelEmpleado.add(btnVerificarEmpleado);
        
        lblInfoEmpleado = new JLabel(" ");
        panelEmpleado.add(lblInfoEmpleado);
        
        // Controles de fecha
        cmbAño = new JComboBox<>();
        cmbMes = new JComboBox<>();
        for (int i = 1; i <= 12; i++) cmbMes.addItem(i);
        
        btnGenerarReporte = new JButton("Generar Reporte");
        btnGenerarDatos = new JButton("Generar Datos Aleatorios");
        
        // Organizar controles
        panelControles.add(panelEmpleado);
        panelControles.add(new JLabel("Año:"));
        panelControles.add(cmbAño);
        panelControles.add(new JLabel("Mes:"));
        panelControles.add(cmbMes);
        panelControles.add(btnGenerarReporte);
        panelControles.add(btnGenerarDatos);

        // Tabla de resultados
        tablaMetricas = new JTable();
        JScrollPane scrollTabla = new JScrollPane(tablaMetricas);
        
        // Panel de gráficos
        panelGraficos = new JPanel(new GridLayout(1, 3));
        
        // Organización del layout
        panelPrincipal.add(panelControles, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelGraficos, BorderLayout.SOUTH);
    }

    public void setAñosDisponibles(List<Integer> añosDeBD) {
        cmbAño.removeAllItems();
        
        // Primero agregar los años que existen en la BD
        for (Integer año : añosDeBD) {
            if (año >= 2010 && año <= 2030) {
                cmbAño.addItem(año);
            }
        }
        
        // Completar con años faltantes
        for (int año = 2030; año >= 2010; año--) {
            if (!existeEnCombo(cmbAño, año)) {
                cmbAño.addItem(año);
            }
        }
        
        // Seleccionar año actual
        Calendar cal = Calendar.getInstance();
        cmbAño.setSelectedItem(cal.get(Calendar.YEAR));
    }

    private boolean existeEnCombo(JComboBox<Integer> combo, int valor) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i) == valor) {
                return true;
            }
        }
        return false;
    }

    public void setEmpleados(Map<Integer, String> empleados) {
        this.mapaEmpleados = empleados;
        configurarAutocompletado();
        
        // Actualizar tooltip con IDs válidos
        if (empleados != null && !empleados.isEmpty()) {
            String idsValidos = "IDs válidos: " + empleados.keySet().stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
            txtEmpleado.setToolTipText(idsValidos);
        }
    }

    private void configurarAutocompletado() {
        if (mapaEmpleados != null) {
            AutoCompleteDecorator.decorate(
                txtEmpleado,
                mapaEmpleados.entrySet().stream()
                    .map(e -> e.getKey() + " - " + e.getValue())
                    .collect(Collectors.toList()),
                false
            );
        }
    }

    public int getSelectedEmpleadoId() throws NumberFormatException {
        String texto = txtEmpleado.getText().trim();
        
        if (texto.isEmpty()) {
            throw new NumberFormatException("Debe ingresar un ID de empleado");
        }
        
        try {
            // Intentar obtener directamente el número
            int id = Integer.parseInt(texto);
            
            // Verificar si existe
            if (mapaEmpleados != null && !mapaEmpleados.containsKey(id)) {
                throw new NumberFormatException("ID " + id + " no existe");
            }
            return id;
        } catch (NumberFormatException e) {
            // Si falla, intentar extraer ID de formato "ID - Nombre"
            if (texto.contains(" - ")) {
                try {
                    String idPart = texto.split(" - ")[0].trim();
                    return Integer.parseInt(idPart);
                } catch (Exception ex) {
                    throw new NumberFormatException("Formato inválido. Use 'ID' o 'ID - Nombre'");
                }
            }
            throw e;
        }
    }

    public void mostrarInfoEmpleado(String info) {
        lblInfoEmpleado.setText(info);
    }

    public void limpiarInfoEmpleado() {
        lblInfoEmpleado.setText(" ");
    }

    // Resto de los métodos permanecen igual...
    public int getAñoSeleccionado() {
        return (int) cmbAño.getSelectedItem();
    }

    public int getMesSeleccionado() {
        return (int) cmbMes.getSelectedItem();
    }

    public void mostrarMetricas(List<MetricaEmpleado> metricas) {
        String[] columnNames = {
            "ID Empleado", "Nombre", "Año", "Mes", "Días Trabajados", 
            "Total Horas", "Promedio Horas/Día", "Horas Extras", 
            "Hora Entrada Más Temprana", "Hora Entrada Más Tardia"
        };
        
        Object[][] data = new Object[metricas.size()][columnNames.length];
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        for (int i = 0; i < metricas.size(); i++) {
            MetricaEmpleado m = metricas.get(i);
            data[i][0] = m.getIdEmpleado();
            data[i][1] = m.getNombreCompleto();
            data[i][2] = m.getAño();
            data[i][3] = m.getMes();
            data[i][4] = m.getDiasTrabajados();
            data[i][5] = String.format("%.2f", m.getTotalHoras());
            data[i][6] = String.format("%.2f", m.getPromedioHorasDia());
            data[i][7] = String.format("%.2f", m.getHorasExtras());
            data[i][8] = timeFormat.format(m.getHoraEntradaMasTemprana());
            data[i][9] = timeFormat.format(m.getHoraEntradaMasTardia());
        }
        
        tablaMetricas.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    public void mostrarGraficos(List<MetricaEmpleado> metricas) {
        panelGraficos.removeAll();
        
        // Gráfico 1: Horas totales por empleado (Barras)
        DefaultCategoryDataset datasetBarras = new DefaultCategoryDataset();
        for (MetricaEmpleado m : metricas) {
            datasetBarras.addValue(m.getTotalHoras(), "Horas Trabajadas", m.getNombreCompleto());
        }
        JFreeChart chartBarras = ChartFactory.createBarChart(
            "Horas Totales por Empleado", "Empleado", "Horas", 
            datasetBarras, PlotOrientation.VERTICAL, true, true, false);
        
        // Gráfico 2: Distribución de horas extras (Torta)
        DefaultPieDataset datasetTorta = new DefaultPieDataset();
        for (MetricaEmpleado m : metricas) {
            datasetTorta.setValue(m.getNombreCompleto(), m.getHorasExtras());
        }
        JFreeChart chartTorta = ChartFactory.createPieChart(
            "Distribución de Horas Extras", datasetTorta, true, true, false);
        
        // Gráfico 3: Promedio de horas por día (Líneas)
        DefaultCategoryDataset datasetLineas = new DefaultCategoryDataset();
        for (MetricaEmpleado m : metricas) {
            datasetLineas.addValue(m.getPromedioHorasDia(), "Promedio", m.getNombreCompleto());
            datasetLineas.addValue(8.0, "Jornada Estándar", m.getNombreCompleto());
        }
        JFreeChart chartLineas = ChartFactory.createLineChart(
            "Promedio de Horas por Día vs Jornada Estándar", 
            "Empleado", "Horas", datasetLineas, PlotOrientation.VERTICAL, true, true, false);
        
        // Agregar gráficos al panel
        panelGraficos.add(new ChartPanel(chartBarras));
        panelGraficos.add(new ChartPanel(chartTorta));
        panelGraficos.add(new ChartPanel(chartLineas));
        
        panelGraficos.revalidate();
        panelGraficos.repaint();
    }

    public void agregarVerificarListener(java.awt.event.ActionListener listener) {
        btnVerificarEmpleado.addActionListener(listener);
    }

    public void agregarListenerGenerarReporte(java.awt.event.ActionListener listener) {
        btnGenerarReporte.addActionListener(listener);
    }

    public void agregarListenerGenerarDatos(java.awt.event.ActionListener listener) {
        btnGenerarDatos.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}