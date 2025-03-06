package vista;

import controlador.UsuariosControlador;
import vista.empleadoAltaVista;
import modelo.EmpleadoDAO;
import modelo.empleadoAlta;
import controlador.UsuariosControlador;
import vista.UsuariosVista;
import modelo.UsuariosModelo;
import controlador.empleadoAltaControlador;

import javax.swing.*;
import java.awt.Dimension;
import javax.swing.text.GapContent;
import modelo.UsuariosModelo;

public class General extends javax.swing.JFrame {
    // Módulo de Nómina
    private javax.swing.JMenu mnuNomina;
    private javax.swing.JMenuItem mnuNominaCalculoNomina;
    private javax.swing.JMenuItem mnuNominaPagos;
    private javax.swing.JMenuItem mnuNominaReportes;

    // Módulo de Gestión de Recursos Humanos
    private javax.swing.JMenu mnuGestionRH;
    private javax.swing.JMenuItem mnuGestionRHAltas;
    private javax.swing.JMenuItem mnuGestionRHBajas;
    private javax.swing.JMenuItem mnuGestionRHHistoriasLaborales;

    // Módulo de Gestión de Prestaciones
    private javax.swing.JMenu mnuPrestaciones;
    private javax.swing.JMenuItem mnuPrestacionesBeneficios;
    private javax.swing.JMenuItem mnuPrestacionesSeguridadSocial;

    // Módulo de Indicadores de Productividad
    private javax.swing.JMenu mnuIndicadores;
    private javax.swing.JMenuItem mnuIndicadoresMetricas;
    private javax.swing.JMenuItem mnuIndicadoresEvaluaciones;

    // Módulo de Reportes
    private javax.swing.JMenu mnuReportes;
    private javax.swing.JMenuItem mnuReportesRH;
    private javax.swing.JMenuItem mnuReportesFinancieros;

    // Módulo Administrativo
    private javax.swing.JMenu mnuAdmin;
    private javax.swing.JMenuItem mnuAdminComunicacion;
    private javax.swing.JMenuItem mnuAdminDocumentacion;
    private javax.swing.JMenuItem mnuAdminCapacitacion;

    // Menú de Ayuda
    private javax.swing.JMenu mnuAyudas;
    private javax.swing.JMenuItem mnuUsuariosRH;

    


    /**
     * Creates new form MdiGeneral
     */
    public General() {
        ImageIcon image = new ImageIcon("src/icons/10612410.png");

        setTitle("Recursos humanos Empresa S.A.");
        setIconImage(image.getImage());//Ponemos una imagen como icono de la ventana
        setLocationRelativeTo(null);

        initComponents();
        setLocationRelativeTo(null);
        setExtendedState(General.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents

    
    private void mnuCatalogosMantenimientosEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {
        
    }

    
    private void initComponents() {
        // ***** Creacion de barra de menus con sus items *****
        jDesktopPane1 = new JDesktopPane();
        jMenuBar1 = new JMenuBar();
        mnuSesion = new JMenu();
        mnuSesionCambiarSesion = new JMenuItem();
        mnuSesionCerrarSesion = new JMenuItem();
    
        // Módulo de Nómina
        mnuNomina = new JMenu("Nómina");
        mnuNominaCalculoNomina = new JMenuItem("Cálculo de Nómina");
        mnuNominaPagos = new JMenuItem("Pagos");
        mnuNominaReportes = new JMenuItem("Reportes de Nómina");
    
        // Módulo de Gestión de Recursos Humanos
        mnuGestionRH = new JMenu("Gestión de RH");
        mnuGestionRHAltas = new JMenuItem("Altas");
        mnuGestionRHBajas = new JMenuItem("Bajas");
        mnuGestionRHHistoriasLaborales = new JMenuItem("Historias Laborales");
    
        // Módulo de Gestión de Prestaciones
        mnuPrestaciones = new JMenu("Prestaciones");
        mnuPrestacionesBeneficios = new JMenuItem("Beneficios");
        mnuPrestacionesSeguridadSocial = new JMenuItem("Seguridad Social");
    
        // Módulo de Indicadores de Productividad
        mnuIndicadores = new JMenu("Indicadores");
        mnuIndicadoresMetricas = new JMenuItem("Métricas");
        mnuIndicadoresEvaluaciones = new JMenuItem("Evaluaciones");
    
        // Módulo de Reportes
        mnuReportes = new JMenu("Reportes");
        mnuReportesRH = new JMenuItem("Reportes de RH");
        mnuReportesFinancieros = new JMenuItem("Reportes Financieros");
    
        // Módulo Administrativo
        mnuAdmin = new JMenu("Administrativo");
        mnuAdminComunicacion = new JMenuItem("Comunicación Interna");
        mnuAdminDocumentacion = new JMenuItem("Documentación");
        mnuAdminCapacitacion = new JMenuItem("Capacitación y Desarrollo");
    
        // Menú de Ayuda
        mnuAyudas = new JMenu("Ayuda");
        mnuUsuariosRH = new JMenuItem("Usuarios RH");
    
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    
        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );
    
        // Configuración del menú de Sesión
        mnuSesion.setText("Sesión");
    
        mnuSesionCambiarSesion = new javax.swing.JMenuItem("Cambiar Usuario");

        // Usar ActionListener anónimo
        mnuSesionCambiarSesion.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("Cambiar de usuario");
            }
        });
        mnuSesion.add(mnuSesionCambiarSesion);
    
        mnuSesionCerrarSesion.setText("Cerrar Sesión");
        mnuSesionCerrarSesion.addActionListener(this::mnuSesionCerrarSesionActionPerformed);
        mnuSesion.add(mnuSesionCerrarSesion);
    
        jMenuBar1.add(mnuSesion);
    
        // Configuración del módulo de Nómina
        mnuNominaCalculoNomina.addActionListener(this::mnuNominaCalculoNominaActionPerformed);
        mnuNominaPagos.addActionListener(this::mnuNominaPagosActionPerformed);
        mnuNominaReportes.addActionListener(this::mnuNominaReportesActionPerformed);
    
        mnuNomina.add(mnuNominaCalculoNomina);
        mnuNomina.add(mnuNominaPagos);
        mnuNomina.add(mnuNominaReportes);
    
        jMenuBar1.add(mnuNomina);
    
        // Configuración del módulo de Gestión de RH
        mnuGestionRHAltas.addActionListener(this::mnuGestionRHAltasActionPerformed);
        mnuGestionRHBajas.addActionListener(this::mnuGestionRHBajasActionPerformed);
        mnuGestionRHHistoriasLaborales.addActionListener(this::mnuGestionRHHistoriasLaboralesActionPerformed);
    
        mnuGestionRH.add(mnuGestionRHAltas);
        mnuGestionRH.add(mnuGestionRHBajas);
        mnuGestionRH.add(mnuGestionRHHistoriasLaborales);
    
        jMenuBar1.add(mnuGestionRH);
    
        // Configuración del módulo de Prestaciones
        mnuPrestacionesBeneficios.addActionListener(this::mnuPrestacionesBeneficiosActionPerformed);
        mnuPrestacionesSeguridadSocial.addActionListener(this::mnuPrestacionesSeguridadSocialActionPerformed);
    
        mnuPrestaciones.add(mnuPrestacionesBeneficios);
        mnuPrestaciones.add(mnuPrestacionesSeguridadSocial);
    
        jMenuBar1.add(mnuPrestaciones);
    
        // Configuración del módulo de Indicadores
        mnuIndicadoresMetricas.addActionListener(this::mnuIndicadoresMetricasActionPerformed);
        mnuIndicadoresEvaluaciones.addActionListener(this::mnuIndicadoresEvaluacionesActionPerformed);
    
        mnuIndicadores.add(mnuIndicadoresMetricas);
        mnuIndicadores.add(mnuIndicadoresEvaluaciones);
    
        jMenuBar1.add(mnuIndicadores);
    
        // Configuración del módulo de Reportes
        mnuReportesRH.addActionListener(this::mnuReportesRHActionPerformed);
        mnuReportesFinancieros.addActionListener(this::mnuReportesFinancierosActionPerformed);
    
        mnuReportes.add(mnuReportesRH);
        mnuReportes.add(mnuReportesFinancieros);
    
        jMenuBar1.add(mnuReportes);
    
        // Configuración del módulo Administrativo
        mnuAdminComunicacion.addActionListener(this::mnuAdminComunicacionActionPerformed);
        mnuAdminDocumentacion.addActionListener(this::mnuAdminDocumentacionActionPerformed);
        mnuAdminCapacitacion.addActionListener(this::mnuAdminCapacitacionActionPerformed);
    
        mnuAdmin.add(mnuAdminComunicacion);
        mnuAdmin.add(mnuAdminDocumentacion);
        mnuAdmin.add(mnuAdminCapacitacion);
    
        jMenuBar1.add(mnuAdmin);
    
        // Menú de Ayuda
        mnuUsuariosRH.addActionListener(this::mnuUsuariosRHActionPerformed);
        mnuAyudas.add(mnuUsuariosRH);
        jMenuBar1.add(mnuAyudas);


    
        setJMenuBar(jMenuBar1);
    
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
    
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuNominaCalculoNominaActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir cálculo de nómina");
    }

    private void mnuNominaPagosActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de pagos");
    }

    private void mnuNominaReportesActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de reportes");
    }

    private void mnuGestionRHAltasActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de altas");

    empleadoAltaVista vista = new empleadoAltaVista();  // Instancia de la vista
    empleadoAlta modelo = new empleadoAlta();           // Instancia del modelo

    // Crear el controlador pasándole las instancias de la vista y el modelo
    empleadoAltaControlador controlador = new empleadoAltaControlador(vista, modelo);

    // Agregar la vista de empleados al jDesktopPane
    jDesktopPane1.add(vista); // Aquí agregas la vista en lugar del controlador

    // Centrar la ventana en el escritorio
    Dimension desktopSize = jDesktopPane1.getSize();
    Dimension frameSize = vista.getSize(); // Usamos la vista, no el controlador
    vista.setLocation((desktopSize.width - frameSize.width) / 2, 
                     (desktopSize.height - frameSize.height) / 2);

    // Hacer visible la ventana
    vista.setVisible(true);

    }

    private void mnuGestionRHBajasActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de bajas");
    }

    private void mnuGestionRHHistoriasLaboralesActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de historias laborales");
    }
    private void mnuPrestacionesBeneficiosActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de beneficios");
    }

    private void mnuPrestacionesSeguridadSocialActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de seguridad social");
    }

    private void mnuIndicadoresMetricasActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de métricas");
    }

    private void mnuIndicadoresEvaluacionesActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de evaluaciones");
    }

    private void mnuReportesRHActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de reportes de RH");
    }

    private void mnuReportesFinancierosActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de reportes financieros");
    }

    private void mnuAdminComunicacionActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de comunicación interna");
    }

    private void mnuAdminDocumentacionActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de documentación");
    }

    private void mnuAdminCapacitacionActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir módulo de capacitación y desarrollo");
    }

    private void mnuUsuariosRHActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir menu de usuarios RH");

        UsuariosVista vista = new UsuariosVista();  // Instancia de la vista
        UsuariosModelo modelo = new UsuariosModelo();           // Instancia del modelo

    // Crear el controlador pasándole las instancias de la vista y el modelo
    UsuariosControlador controlador = new UsuariosControlador (vista, modelo);

    // Agregar la vista de empleados al jDesktopPane
    jDesktopPane1.add(vista); // Aquí agregas la vista en lugar del controlador

    // Centrar la ventana en el escritorio
    Dimension desktopSize = jDesktopPane1.getSize();
    Dimension frameSize = vista.getSize(); // Usamos la vista, no el controlador
    vista.setLocation((desktopSize.width - frameSize.width) / 2, 
                     (desktopSize.height - frameSize.height) / 2);

    // Hacer visible la ventana
    vista.setVisible(true);

    vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    // ****** Funcionalidad de botones y barras de menu ******
    private void mnuCatalogosMantenimientosPuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCatalogosMantenimientosCursosActionPerformed
        // TODO add your handling code here:    
        /*MantenimientoCursos ventana = new MantenimientoCursos();
        jDesktopPane1.add(ventana);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension FrameSize = ventana.getSize();
        ventana.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        */
    }//GEN-LAST:event_mnuCatalogosMantenimientosCursosActionPerformed

    private void mnuArchivoAbrirSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuArchivoAbrirSesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuArchivoAbrirSesionActionPerformed

    private void mnuSesionCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuArchivoAbrirSesionActionPerformed
        Login login = new Login();
        dispose();
    }//GEN-LAST:event_mnuArchivoAbrirSesionActionPerformed

    private void mnuCatalogosMantenimientosMaestrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCatalogosMantenimientosMaestrosActionPerformed
        // TODO add your handling code here:
        /*MantenimientoMaestros ventana1 = new MantenimientoMaestros();
        jDesktopPane1.add(ventana1);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension FrameSize = ventana1.getSize();
        ventana1.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        */
    }//GEN-LAST:event_mnuCatalogosMantenimientosMaestrosActionPerformed


    private void mnuSeguridadBitacoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSeguridadBitacoraActionPerformed
        // TODO add your handling code here:
        /*frmMantenimientoBitacora ventana = new frmMantenimientoBitacora();
        jDesktopPane1.add(ventana);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension FrameSize = ventana.getSize();
        ventana.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        */
    }//GEN-LAST:event_mnuSeguridadBitacoraActionPerformed

    private void mnuCatalogosMantenimientosTiposDePagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCatalogosMantenimientosTiposDePagosActionPerformed
        /*MantenimientoPagos ventana = new MantenimientoPagos();
        jDesktopPane1.add(ventana);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension FrameSize = ventana.getSize();
        ventana.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        */
    }//GEN-LAST:event_mnuCatalogosMantenimientosTiposDePagosActionPerformed

    private void mnuProcesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuProcesosActionPerformed
        // TODO add your handling code here:
        /*ProcesoTesoreria ventana = new ProcesoTesoreria();
        jDesktopPane1.add(ventana);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension FrameSize = ventana.getSize();
        ventana.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        ventana.setVisible(true);
        */
    }//GEN-LAST:event_mnuProcesosActionPerformed

    private void ProcesoTesoreriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProcesoTesoreriaActionPerformed
        // TODO add your handling code here:
        /*ProcesoTesoreria ventana = new ProcesoTesoreria();
        jDesktopPane1.add(ventana);
        Dimension desktopSize = jDesktopPane1.getSize();
        Dimension FrameSize = ventana.getSize();
        ventana.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        ventana.setVisible(true);
        */
    }//GEN-LAST:event_ProcesoTesoreriaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(General.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(General.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(General.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(General.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new General().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ProcesoTesoreria;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu mnuSesion;
    private javax.swing.JMenuItem mnuSesionCambiarSesion;
    private javax.swing.JMenuItem mnuSesionCerrarSesion;
    private javax.swing.JMenu mnuCatalogos;
    private javax.swing.JMenu mnuCatalogosMantenimiento;
    private javax.swing.JMenuItem mnuCatalogosMantenimientosPuestos;
    private javax.swing.JMenuItem mnuCatalogosMantenimientosEmpleados;
    private javax.swing.JMenuItem mnuCatalogosMantenimientosTiposDePagos;
    private javax.swing.JMenu mnuProcesos;
    private javax.swing.JMenu mnuSeguridad;
    private javax.swing.JMenuItem mnuSeguridadBitacora;
    // End of variables declaration//GEN-END:variables
}


