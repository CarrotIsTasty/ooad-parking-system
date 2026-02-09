package parkingapp;

import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class ExitParkingPage extends javax.swing.JFrame {
    private final String plate;
    private JPanel ParkingFeeSummaryButtonPanel;
    
    public ExitParkingPage(String plate) {
        this.plate = plate;
        initComponents();
        ParkingFeeSummaryButtonPanel = new JPanel();
        initParkingFeeSummary();
        ParkingFeeSummaryPanel.setVisible(true);
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        TimeLabel.setText("Time Now: " + now.format(formatter));
    }
    
    private void initParkingFeeSummary() {
        ParkingFeeSummaryPanel.setLayout(new GridLayout(0, 1, 10, 10));

        JLabel vehicleTypeLabel = new JLabel("Vehicle Type    : ");
        vehicleTypeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        vehicleTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel plateNumberLabel = new JLabel("Plate Number  : " + plate);
        plateNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        plateNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel floorLabel = new JLabel("Floor Number : ");
        floorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        floorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel parkingSpotLabel = new JLabel("Parking Spot  : F-R-S");
        parkingSpotLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingSpotLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel parkingRateLabel = new JLabel("Parking Rate  : /hour");
        parkingRateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingRateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel parkingDurationLabel = new JLabel("Duration    : Hours");
        parkingDurationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingDurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel parkingFeeLabel = new JLabel("Parking Fee : RM ");
        parkingFeeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingFeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel parkingFineLabel = new JLabel("Parking Fine : RM ");
        parkingFineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingFineLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel parkingTotalFeeLabel = new JLabel("Total Fee : RM ");
        parkingTotalFeeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingTotalFeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        initParkingFeeSummaryButton();
        
        ParkingFeeSummaryPanel.add(vehicleTypeLabel);
        ParkingFeeSummaryPanel.add(plateNumberLabel);
        ParkingFeeSummaryPanel.add(floorLabel);
        ParkingFeeSummaryPanel.add(parkingSpotLabel);
        ParkingFeeSummaryPanel.add(parkingRateLabel);
        
        ParkingFeeSummaryPanel.add(parkingDurationLabel);
        ParkingFeeSummaryPanel.add(parkingFeeLabel);
        ParkingFeeSummaryPanel.add(parkingFineLabel);
        ParkingFeeSummaryPanel.add(parkingTotalFeeLabel);
        
        ParkingFeeSummaryPanel.add(ParkingFeeSummaryButtonPanel);
        
        ParkingFeeSummaryPanel.revalidate();
        ParkingFeeSummaryPanel.repaint();
    }

    private void initParkingFeeSummaryButton() {
        ParkingFeeSummaryButtonPanel = new JPanel();
        ParkingFeeSummaryButtonPanel.setLayout(new GridLayout(0, 2, 10, 10));
        JButton backButton = new JButton("Back");
        JButton payButton = new JButton("Pay");

        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        payButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        backButton.addActionListener(e -> {
            dispose();
        });
        payButton.addActionListener(e -> {
        });

        ParkingFeeSummaryButtonPanel.add(backButton);
        ParkingFeeSummaryButtonPanel.add(payButton);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ParkingFeeSummaryPanel = new javax.swing.JPanel();
        TimeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout ParkingFeeSummaryPanelLayout = new javax.swing.GroupLayout(ParkingFeeSummaryPanel);
        ParkingFeeSummaryPanel.setLayout(ParkingFeeSummaryPanelLayout);
        ParkingFeeSummaryPanelLayout.setHorizontalGroup(
            ParkingFeeSummaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        ParkingFeeSummaryPanelLayout.setVerticalGroup(
            ParkingFeeSummaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 359, Short.MAX_VALUE)
        );

        TimeLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        TimeLabel.setText("Time: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ParkingFeeSummaryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(181, Short.MAX_VALUE)
                .addComponent(TimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(TimeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ParkingFeeSummaryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(233, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(ExitParkingPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExitParkingPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExitParkingPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExitParkingPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExitParkingPage("TEST123").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ParkingFeeSummaryPanel;
    private javax.swing.JLabel TimeLabel;
    // End of variables declaration//GEN-END:variables
}
