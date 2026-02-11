
package JavaForm;

import Admin.AdminLoginGUI;
import JavaForm.OnEntryPage;
import java.awt.event.*;
import javax.swing.*;

public class StartPage extends javax.swing.JFrame {
    private int clickCount = 0;
    public StartPage() {
        initComponents();
        launchAdminGUI();
    }
    private void launchAdminGUI(){
        
        TitleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickCount++;
                if (clickCount == 5) {
                    new AdminLoginGUI();
                    clickCount = 0;
                }
            }
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TitleLabel = new javax.swing.JLabel();
        ParkingButton = new javax.swing.JButton();
        ReserveParking = new javax.swing.JButton();
        ExitParkingButton = new javax.swing.JButton();
        CloseButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        TitleLabel.setText("University Parking System");

        ParkingButton.setText("Start Parking");
        ParkingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ParkingButtonActionPerformed(evt);
            }
        });

        ReserveParking.setText("Reserve Parking");
        ReserveParking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReserveParkingActionPerformed(evt);
            }
        });

        ExitParkingButton.setText("Exit Parking");
        ExitParkingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitParkingButtonActionPerformed(evt);
            }
        });

        CloseButton.setText("Close");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(TitleLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ParkingButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ReserveParking, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(ExitParkingButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CloseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(TitleLabel)
                .addGap(49, 49, 49)
                .addComponent(ParkingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ReserveParking, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ExitParkingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(226, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ReserveParkingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReserveParkingActionPerformed
        new OnEntryPage(OnEntryPage.Mode.RESERVE).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ReserveParkingActionPerformed

    private void ExitParkingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitParkingButtonActionPerformed
        new OnEntryPage(OnEntryPage.Mode.EXIT).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ExitParkingButtonActionPerformed

    private void ParkingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ParkingButtonActionPerformed
        new OnEntryPage(OnEntryPage.Mode.PARKING).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ParkingButtonActionPerformed

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_CloseButtonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JButton ExitParkingButton;
    private javax.swing.JButton ParkingButton;
    private javax.swing.JButton ReserveParking;
    private javax.swing.JLabel TitleLabel;
    // End of variables declaration//GEN-END:variables
}
