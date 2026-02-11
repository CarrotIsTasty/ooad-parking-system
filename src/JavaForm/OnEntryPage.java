package JavaForm;

import JavaForm.OnExitPage;
import JavaForm.AvailableParkingPage;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import parkingapp.VehicleType;
import static parkingapp.TestingPage.ceilToNext30Minutes;


public class OnEntryPage extends javax.swing.JFrame {
    private final DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public enum Mode {PARKING, RESERVE, EXIT}
    private final Mode mode;
    
    public OnEntryPage(Mode mode) {
        initComponents();
        this.mode = mode;
        LocalDateTime now = LocalDateTime.now();
        TimeLabel.setText("Time Now: " + now.format(timeFmt));
        applyMode();
    }
    
    private void applyMode() {
        if (mode == Mode.RESERVE) {
            ReserveTimeComboBox.setVisible(true);
            ReserveLabel.setVisible(true);
            ReserveHourGenerator();
        } else if (mode == Mode.PARKING){
            ReserveTimeComboBox.setVisible(false);
            ReserveLabel.setVisible(false);
        } else if (mode == Mode.EXIT){
            ReserveTimeComboBox.setVisible(false);
            ReserveLabel.setVisible(false);
            VehicleTypeComboBox.setVisible(false);
            VehicleTypeLabel.setVisible(false);
        }

        this.revalidate();
        this.repaint();           
    }
    
    private static LocalDateTime ceilToNext30Minutes(LocalDateTime time) {
        int minute = time.getMinute();
        int remainder = minute % 30;
        if (remainder == 0) {return time.withSecond(0).withNano(0);}
        return time.plusMinutes(30 - remainder).withSecond(0).withNano(0);
    }
    
    private void ReserveHourGenerator() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime ceiledTime = ceilToNext30Minutes(timeNow);
        String reserveTime30m = ceiledTime.format(formatter);
        String reserveTime60m = ceiledTime.plusMinutes(30).format(formatter);
        String reserveTime90m = ceiledTime.plusMinutes(60).format(formatter);
        ReserveTimeComboBox.addItem(reserveTime30m);
        ReserveTimeComboBox.addItem(reserveTime60m);
        ReserveTimeComboBox.addItem(reserveTime90m);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TimeLabel = new javax.swing.JLabel();
        VehicleTypeComboBox = new javax.swing.JComboBox<>();
        VehicleTypeLabel = new javax.swing.JLabel();
        PlateNumberFormatText = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        NextButton = new javax.swing.JButton();
        CloseButton = new javax.swing.JButton();
        ReserveTimeComboBox = new javax.swing.JComboBox<>();
        ReserveLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TimeLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        TimeLabel.setText("Time: ");

        VehicleTypeComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        VehicleTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CAR", "SUV", "TRUCK", "MOTOCYCLE", "BICYCLE", "HANDICAPPED VEHICLE" }));

        VehicleTypeLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        VehicleTypeLabel.setText("Vehicle Type:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Plate Number: ");

        NextButton.setText("Next");
        NextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextButtonActionPerformed(evt);
            }
        });

        CloseButton.setText("Close");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        ReserveTimeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReserveTimeComboBoxActionPerformed(evt);
            }
        });

        ReserveLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ReserveLabel.setText("Reserve Time:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ReserveLabel)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(CloseButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(NextButton))
                            .addComponent(jLabel2)
                            .addComponent(VehicleTypeLabel)
                            .addComponent(VehicleTypeComboBox, 0, 195, Short.MAX_VALUE)
                            .addComponent(PlateNumberFormatText, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                            .addComponent(ReserveTimeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(TimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(TimeLabel)
                .addGap(18, 18, 18)
                .addComponent(VehicleTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VehicleTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PlateNumberFormatText, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ReserveLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReserveTimeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NextButton)
                    .addComponent(CloseButton))
                .addContainerGap(198, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void NextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextButtonActionPerformed
        int typeIndex = VehicleTypeComboBox.getSelectedIndex();
        VehicleType type;
        switch (typeIndex) {
            case 0: type = VehicleType.CAR; break;
            case 1: type = VehicleType.SUV; break;
            case 2: type = VehicleType.TRUCK; break;
            case 3: type = VehicleType.MOTORCYCLE; break;
            case 4: type = VehicleType.BICYCLE; break;
            case 5: type = VehicleType.HANDICAPPED; break;
            default: throw new AssertionError();
        }
        String plate = PlateNumberFormatText.getText();
        if (plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter plate number.");
            return;
        }
        String timeStamp = (String) ReserveTimeComboBox.getSelectedItem();
        System.out.println(timeStamp);
        if (mode == Mode.RESERVE) {
            new AvailableParkingPage(type, plate, timeStamp).setVisible(true);
        } else if (mode == Mode.PARKING){
            new AvailableParkingPage(type, plate).setVisible(true);
        } else if (mode == Mode.EXIT){
            //JEVAAN's PART
            //Search plate from databate
            //If plate does not exist -> Generate JOptionPane "Your vehicle does not exist in our system"
            new OnExitPage(plate).setVisible(true);
        }
        this.dispose();
    }//GEN-LAST:event_NextButtonActionPerformed

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        new StartPage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void ReserveTimeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReserveTimeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReserveTimeComboBoxActionPerformed
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OnEntryPage(Mode.PARKING).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JButton NextButton;
    private javax.swing.JFormattedTextField PlateNumberFormatText;
    private javax.swing.JLabel ReserveLabel;
    private javax.swing.JComboBox<String> ReserveTimeComboBox;
    private javax.swing.JLabel TimeLabel;
    private javax.swing.JComboBox<String> VehicleTypeComboBox;
    private javax.swing.JLabel VehicleTypeLabel;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
