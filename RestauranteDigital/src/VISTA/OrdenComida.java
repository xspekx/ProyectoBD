/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VISTA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author reyna
 */
public class OrdenComida extends javax.swing.JFrame {
    Connection con = null;
    Statement stnt = null;
    String rut = Login.jTextField1.getText();
    /**
     * Creates new form OrdenComida
     */
    public OrdenComida() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setFocusable(true);    
        
        
                
    try {
    String url = "jdbc:mysql://localhost:3306/restaurante"; // direccion donde se encuentra la base de datos
    String usuario = "root"; // usuario de la gestion de base de datos
    String contraseña = "123"; // contraseña para entrar a la base de datos
                
    Class.forName("com.mysql.jdbc.Driver").newInstance(); // carga el driver para conectarce
    con = (Connection) DriverManager.getConnection(url,usuario,contraseña); // se conecta a la base de datos nuestro programa 
    if(con != null){
                System.out.println("Conexion Exitosa!");
            }else{
                System.out.println("Conexion Fallida!");                
            }
    
    
    }catch(Exception e){// excepciones en el caso de haber un error
         Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null,e); 
    }
        
        DefaultTableModel modeloTabla = new DefaultTableModel();
        OrdenComida.jTable1.setModel(modeloTabla);
        modeloTabla.setColumnIdentifiers(new Object[]{"UNIDAD","DETALLE","PRECIO UND.","PRECIO TOTAL"});//

        try { // condicion en caso de error
        stnt = con.createStatement();
        ResultSet rsl = stnt.executeQuery("SELECT detalle.cantidad, producto.nombre, producto.precio, detalle.precio AS 'total'" +
                                          " FROM detalle" +
                                          " INNER JOIN producto ON producto.id = detalle.producto_id_fk" +
                                          " WHERE detalle.factura_id_fk = (SELECT MAX(factura.id)" +
                                          " FROM usuario INNER JOIN factura ON factura.usuario_id_fk = usuario.id WHERE usuario.rut = '"+rut+"' AND pagado = 1)");
       
        
        
         while(rsl.next()){
                modeloTabla.addRow(new Object[]{rsl.getInt("detalle.cantidad"),rsl.getString("nombre"),rsl.getInt("precio"),rsl.getInt("total")});}
         
         
         
         
         ResultSet rs2 = stnt.executeQuery("SElECT mesa_id_fk FROM factura WHERE id =(SELECT MAX(factura.id)" +
                                          " FROM usuario INNER JOIN factura ON factura.usuario_id_fk = usuario.id WHERE usuario.rut = '"+rut+"')");
         if(rs2.next()){
             mesa.setText(String.valueOf(rs2.getInt("mesa_id_fk")));
         }
            }catch (SQLException ex) {
        }
        
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mesa = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(570, 670));
        getContentPane().setLayout(null);

        mesa.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        mesa.setForeground(new java.awt.Color(255, 102, 0));
        mesa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mesaMouseClicked(evt);
            }
        });
        getContentPane().add(mesa);
        mesa.setBounds(210, 420, 120, 60);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 102, 0));
        jLabel15.setText("MESA");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel15);
        jLabel15.setBounds(60, 420, 140, 60);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 54)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 102, 0));
        jLabel7.setText("DETALLE");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(160, 20, 260, 60);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 102, 0));
        jLabel14.setText("Salir");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel14);
        jLabel14.setBounds(230, 490, 120, 60);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(60, 100, 452, 320);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENES/93b5f9913d2e4316cd6e541c67b9aed0.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 570, 770);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel15MouseClicked

    private void mesaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mesaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_mesaMouseClicked

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
            java.util.logging.Logger.getLogger(OrdenComida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrdenComida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrdenComida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrdenComida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrdenComida().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable1;
    private javax.swing.JLabel mesa;
    // End of variables declaration//GEN-END:variables
}
