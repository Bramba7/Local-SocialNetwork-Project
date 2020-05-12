/*
 * Licensed to Team 3 LocalsJavaApp LLC 
 */
package home;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author zackdavis
 * @author Fernando Brambilla de Mello
 */
public final class UserProfile extends javax.swing.JFrame {
    home.Connection x = new home.Connection();
    public String type = "";
    public int userInfo;

    public UserProfile() throws SQLException {

        initComponents();
        getID();
        clearTable();
        clearTableAct();
        show_locals();
        show_localsAct();
        messageWelcome();

    }
//get Info from txt
    public void getID() {
        try {

            BufferedReader br = new BufferedReader(new FileReader("cookie.txt"));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                userInfo = Integer.parseInt(strLine);
            }
        } catch (IOException | NumberFormatException e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        } finally {

        }
    }
    public void messageWelcome() throws SQLException{
        String first;
        String last;
        first = x.genericPerson("firstname", "person_id" , Integer.toString(userInfo) );
        last = x.genericPerson("lastname", "person_id" , Integer.toString(userInfo) );
        lb_nameUser.setText("Welcome user "+ first + " " +last + " to Locals App."  );
        
    }

    public void clearTable() {
        DefaultTableModel dm = (DefaultTableModel) local_Display.getModel();
        while (dm.getRowCount() > 0) {
            dm.removeRow(0);
        }
    }

    public void clearTableAct() {
        DefaultTableModel dn = (DefaultTableModel) act_Display.getModel();
        while (dn.getRowCount() > 0) {
            dn.removeRow(0);
        }
    }

    public ArrayList<Localusers> localsFeed(String SQL) throws SQLException {
        ArrayList<Localusers> localsFeed = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        conn = DriverManager.getConnection("jdbc:mysql://45.49.254.49:3306/locals", "root", "toor");
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Localusers lu;
        try (ResultSet rs = stmt.executeQuery(SQL)) {
            if (rs.next() == false) {
                System.out.println("ResultSet in empty in Java");

            } else {
                do {
                    lu = new Localusers(rs.getInt("act_code"), rs.getString("act_type"), rs.getString("act_date"), rs.getString("act_time"),rs.getString("act_location"), rs.getString("firstname"));
                    localsFeed.add(lu);
                } while (rs.next());
            }
        } catch (Exception e) {
            System.err.println("error");
            JOptionPane.showMessageDialog(null, e);

        }
        return localsFeed;
    }



    public void show_locals() throws SQLException {
        
        String query = "select * \n"
                + "from locals_person P\n"
                + "Join locals_act A \n"
                + "ON P.person_id = A.act_creator " + type + "\n"
                + "order by P.firstname  ";
        
        ArrayList<Localusers> list = localsFeed(query);
        DefaultTableModel model = (DefaultTableModel) local_Display.getModel();
        Object[] row = new Object[1000];
        for (int i = 0; i < list.size(); i++) {
           row[0] = list.get(i).getCodeAct();
            row[1] = list.get(i).getActType();
            row[2] = list.get(i).getDay();
            row[3] = list.get(i).getTime();
            row[4] = list.get(i).getLocation();
            row[5] = list.get(i).getFirstname();
            model.addRow(row);

        }
    }

    public void show_localsAct() throws SQLException {
        
        String queryTable = " select *\n"
                + "from locals_person P\n"
                + "Join locals_act A ON P.person_id = A.act_creator \n"
                + "join locals_enroll E on E.act_code = A.act_code  and E.enroll_user = " + userInfo + " ";
        ArrayList<Localusers> list = localsFeed(queryTable);
        DefaultTableModel model2 = (DefaultTableModel) act_Display.getModel();
        Object[] row = new Object[1000];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getCodeAct();
            row[1] = list.get(i).getActType();
            row[2] = list.get(i).getDay();
            row[3] = list.get(i).getTime();
            row[4] = list.get(i).getLocation();
            row[5] = list.get(i).getFirstname();
            model2.addRow(row);

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

        pTurist = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        logout = new javax.swing.JButton();
        update = new javax.swing.JButton();
        messages = new javax.swing.JButton();
        beALocal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        local_Display = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        act_Display = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        search = new javax.swing.JButton();
        jCombo_type = new javax.swing.JComboBox<String>();
        reset = new javax.swing.JButton();
        lb_nameUser = new javax.swing.JLabel();
        lb_back = new javax.swing.JLabel();
        pActivityManeger = new javax.swing.JPanel();
        backBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jText_codAct = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jB_addAct = new javax.swing.JButton();
        jB_deleteAct = new javax.swing.JButton();
        back_ground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Locals"); // NOI18N
        setResizable(false);
        setSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new java.awt.CardLayout());

        pTurist.setBackground(new java.awt.Color(255, 255, 255));
        pTurist.setSize(new java.awt.Dimension(800, 600));
        pTurist.setLayout(null);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/home/Img/logo.png"))); // NOI18N
        jLabel1.setToolTipText("");
        pTurist.add(jLabel1);
        jLabel1.setBounds(40, 80, 190, 190);

        logout.setText("Logout");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        pTurist.add(logout);
        logout.setBounds(10, 510, 250, 53);

        update.setText("Refresh the page");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        pTurist.add(update);
        update.setBounds(10, 290, 250, 53);

        messages.setText("Messages");
        messages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messagesActionPerformed(evt);
            }
        });
        pTurist.add(messages);
        messages.setBounds(10, 370, 250, 53);

        beALocal.setText("Activity Manager");
        beALocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beALocalActionPerformed(evt);
            }
        });
        pTurist.add(beALocal);
        beALocal.setBounds(10, 440, 250, 53);

        local_Display.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Activity code", "Type", "Day", "Time", "Location", "Creator"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        local_Display.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(local_Display);

        pTurist.add(jScrollPane1);
        jScrollPane1.setBounds(270, 100, 520, 250);

        act_Display.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Activity Code", "Type", "Day", "Time", "Location", "Creator"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(act_Display);

        pTurist.add(jScrollPane3);
        jScrollPane3.setBounds(270, 390, 520, 170);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel3.setText("Available Activities");
        pTurist.add(jLabel3);
        jLabel3.setBounds(420, 20, 240, 30);

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel4.setText("Your Activities");
        pTurist.add(jLabel4);
        jLabel4.setBounds(440, 350, 180, 30);

        search.setText("Search");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        pTurist.add(search);
        search.setBounds(460, 60, 137, 29);

        jCombo_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Surfing", "Snowboarding", "Hiking", "Fishing ", "Sailing", "Cycling", "Skiing", "Rock Climbing", "Camping", "Skateboarding", "City Tours ", "Art", "Museums", "Restaurants ", "Nightlife " }));
        jCombo_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCombo_typeActionPerformed(evt);
            }
        });
        pTurist.add(jCombo_type);
        jCombo_type.setBounds(270, 60, 180, 27);

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        pTurist.add(reset);
        reset.setBounds(620, 60, 137, 29);

        lb_nameUser.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        lb_nameUser.setText("msg");
        pTurist.add(lb_nameUser);
        lb_nameUser.setBounds(10, 20, 390, 19);

        lb_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/home/Img/background.png"))); // NOI18N
        pTurist.add(lb_back);
        lb_back.setBounds(0, 0, 800, 600);

        getContentPane().add(pTurist, "card2");

        pActivityManeger.setBackground(new java.awt.Color(255, 255, 255));
        pActivityManeger.setPreferredSize(new java.awt.Dimension(800, 600));
        pActivityManeger.setLayout(null);

        backBtn.setText("Back");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        pActivityManeger.add(backBtn);
        backBtn.setBounds(6, 6, 75, 29);

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Activity Control");
        pActivityManeger.add(jLabel2);
        jLabel2.setBounds(150, 60, 495, 55);
        pActivityManeger.add(jText_codAct);
        jText_codAct.setBounds(330, 260, 100, 28);

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel6.setText("Enter activity code to add or delete.");
        pActivityManeger.add(jLabel6);
        jLabel6.setBounds(230, 220, 310, 30);

        jB_addAct.setText("Add");
        jB_addAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_addActActionPerformed(evt);
            }
        });
        pActivityManeger.add(jB_addAct);
        jB_addAct.setBounds(260, 310, 97, 29);

        jB_deleteAct.setText("Delete");
        jB_deleteAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_deleteActActionPerformed(evt);
            }
        });
        pActivityManeger.add(jB_deleteAct);
        jB_deleteAct.setBounds(410, 310, 97, 29);

        back_ground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/home/Img/background.png"))); // NOI18N
        pActivityManeger.add(back_ground);
        back_ground.setBounds(0, 0, 800, 600);

        getContentPane().add(pActivityManeger, "card3");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void beALocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beALocalActionPerformed
        // TODO add your handling code here:
        pActivityManeger.setVisible(true);
        pTurist.setVisible(false);
    }//GEN-LAST:event_beALocalActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        // TODO add your handling code here:
        jText_codAct.setText("");
        clearTable();
        clearTableAct();
        try {
            show_locals();
            show_localsAct();
        } catch (SQLException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pActivityManeger.setVisible(false);
                
    }//GEN-LAST:event_backBtnActionPerformed

    private void jCombo_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCombo_typeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCombo_typeActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        // TODO add your handling code here:

        clearTable();
        clearTableAct();
        try {
            show_localsAct();
            show_locals();
        } catch (SQLException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_updateActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:

        Home n = new Home();
        n.setVisible(true);
        dispose();
    }//GEN-LAST:event_logoutActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        clearTable();
        this.type = "";
        try {
            show_locals();
        } catch (SQLException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_resetActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
        clearTable();
        this.type = "and A.act_type = '" + jCombo_type.getSelectedItem() + "'";
        try {

            show_locals();
        } catch (SQLException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_searchActionPerformed

    private void jB_addActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_addActActionPerformed
        try {
            if (x.activityCheckExistUser( userInfo , jText_codAct.getText()) == 0){
                
              if (x.activityCheck(jText_codAct.getText()) == 1){
                String sqlAdd = "INSERT INTO locals_enroll(act_code,enroll_user) "
                        + "VALUES (" + jText_codAct.getText() + "," + userInfo + " )";
                x.query(sqlAdd);
                JOptionPane.showMessageDialog(null, "Activity " + jText_codAct.getText() + " was successfully added", "", JOptionPane.PLAIN_MESSAGE);
                }else{
                System.err.println("Error Activity Add 1");     
                JOptionPane.showMessageDialog(null, "Activity does not exist", "Erro", JOptionPane.WARNING_MESSAGE);
              }
            }else
                JOptionPane.showMessageDialog(null, "You are already enrolled in this activity", "Erro", JOptionPane.WARNING_MESSAGE);
                System.err.println("Error Activity Add 2");
        } catch (SQLException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jB_addActActionPerformed

    private void jB_deleteActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_deleteActActionPerformed
        try {
            if (x.activityCheckExistUser( userInfo , jText_codAct.getText()) == 1){
                
                String sqlAdd = "delete from locals_enroll where act_code = " + jText_codAct.getText() + " and enroll_user = " + userInfo + " ";
                       
                x.query(sqlAdd);
                JOptionPane.showMessageDialog(null, "Activity " + jText_codAct.getText() + " was deleted successfully", "", JOptionPane.PLAIN_MESSAGE);
            }else{
                System.err.print("Error Activity deleted");
                JOptionPane.showMessageDialog(null, "Something wrong!!, try again!!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jB_deleteActActionPerformed

    private void messagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messagesActionPerformed
        System.out.println("Messages go here");
        JFrame chatWindow = new MessagingClient(userInfo);
        chatWindow.setVisible(true);
      //              dispose();   
    }//GEN-LAST:event_messagesActionPerformed

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
            java.util.logging.Logger.getLogger(UserProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new UserProfile().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable act_Display;
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel back_ground;
    private javax.swing.JButton beALocal;
    private javax.swing.JButton jB_addAct;
    private javax.swing.JButton jB_deleteAct;
    private javax.swing.JComboBox<String> jCombo_type;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jText_codAct;
    private javax.swing.JLabel lb_back;
    private javax.swing.JLabel lb_nameUser;
    private javax.swing.JTable local_Display;
    private javax.swing.JButton logout;
    private javax.swing.JButton messages;
    private javax.swing.JPanel pActivityManeger;
    private javax.swing.JPanel pTurist;
    private javax.swing.JButton reset;
    private javax.swing.JButton search;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
