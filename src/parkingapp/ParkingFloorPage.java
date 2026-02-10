
package parkingapp;

import java.awt.GridLayout;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;

public class ParkingFloorPage extends javax.swing.JFrame {
    private final DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
    private Integer selectedFloor = null;
    private Integer selectedRow = null;
    private Integer selectedSpot = null;

    private final VehicleType type;
    private final String plate;
    private final String reserveTimeStamp;
    
    public ParkingFloorPage(VehicleType type, String plate) {
        this(type, plate, null);
    }
    
    public ParkingFloorPage(VehicleType type, String plate, String reserveTimeStamp) {
        initComponents();
        this.type = type;
        this.plate = plate;
        this.reserveTimeStamp = reserveTimeStamp;
        System.out.println("Parking Floor" + reserveTimeStamp);
        SpotPanel.setVisible(false);
        RowPanel.setVisible(false);
        FloorsPanel.setVisible(true);
        
        ConfirmButton.setEnabled(false);
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        TimeLabel.setText("Time Now: " + now.format(formatter));
        initFloorButton();
    }
    
    public void initFloorButton() {
        FloorsPanel.setLayout(new GridLayout(0, 1, 10, 10));
        int totalFloors = 5;

        FloorsPanel.removeAll();

        for (int i = 1; i <= totalFloors; i++) {
            JButton floorBtn = new JButton("Floor " + i);
            int floorNumber = i;

            floorBtn.addActionListener(e -> {
                selectedFloor = floorNumber;
                selectedRow = null;
                selectedSpot = null;
                SelectedFloorLabel.setText("Selected Floor: " + floorNumber);
                SelectedRowLabel.setText("Selected Row: -");
                SelectedSpotLabel.setText("Selected Spot: -");

                FloorsPanel.setVisible(false);

                initRowButton(floorNumber);
                RowPanel.setVisible(true);

                getContentPane().revalidate();
                getContentPane().repaint();
            });

            FloorsPanel.add(floorBtn);
        }

        FloorsPanel.revalidate();
        FloorsPanel.repaint();
    }
    
    public void initRowButton(int floorNumber) {
        RowPanel.setLayout(new GridLayout(0, 2, 10, 10));
        int totalRow = 6;

        RowPanel.removeAll();

        for (int i = 1; i <= totalRow; i++) {
            JButton rowBtn = new JButton("Row " + i);
            int rowNumber = i;

            rowBtn.addActionListener(e -> {
                selectedRow = rowNumber;
                selectedSpot = null;
                System.out.println("Floor " + floorNumber + ", Row " + rowNumber);
                SelectedRowLabel.setText("Selected Row: " + rowNumber);
                SelectedSpotLabel.setText("Selected Spot: -");
                RowPanel.setVisible(false);
                initSpotButton(floorNumber, rowNumber);
                SpotPanel.setVisible(true);
                getContentPane().revalidate();
                getContentPane().repaint();
            });

            RowPanel.add(rowBtn);
        }

        RowPanel.revalidate();
        RowPanel.repaint();
    }
    
    public void initSpotButton(int floorNumber, int rowNumber) {
        SpotPanel.setLayout(new GridLayout(0, 3, 10, 10));
        int totalSpot = 3;

        SpotPanel.removeAll();

        for (int i = 1; i <= totalSpot; i++) {
            JButton spotBtn = new JButton("Spot " + i);
            int spotNumber = i;

            spotBtn.addActionListener(e -> {
                selectedSpot = spotNumber;
                System.out.println("Floor " + floorNumber + ", Row " + rowNumber + ", Spot " + spotNumber);
                SelectedSpotLabel.setText("Selected Spot: " + spotNumber);
                ConfirmButton.setEnabled(true);
            });

            SpotPanel.add(spotBtn);
        }

        SpotPanel.revalidate();
        SpotPanel.repaint();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FloorsPanel = new javax.swing.JPanel();
        TimeLabel = new javax.swing.JLabel();
        RowPanel = new javax.swing.JPanel();
        SelectedFloorLabel = new javax.swing.JLabel();
        SelectedRowLabel = new javax.swing.JLabel();
        SelectedSpotLabel = new javax.swing.JLabel();
        SpotPanel = new javax.swing.JPanel();
        ConfirmButton = new javax.swing.JButton();
        BackButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout FloorsPanelLayout = new javax.swing.GroupLayout(FloorsPanel);
        FloorsPanel.setLayout(FloorsPanelLayout);
        FloorsPanelLayout.setHorizontalGroup(
            FloorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        FloorsPanelLayout.setVerticalGroup(
            FloorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 111, Short.MAX_VALUE)
        );

        TimeLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        TimeLabel.setText("Time: ");

        javax.swing.GroupLayout RowPanelLayout = new javax.swing.GroupLayout(RowPanel);
        RowPanel.setLayout(RowPanelLayout);
        RowPanelLayout.setHorizontalGroup(
            RowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        RowPanelLayout.setVerticalGroup(
            RowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        SelectedFloorLabel.setText("Selected Floor: ");

        SelectedRowLabel.setText("Selected Row: ");

        SelectedSpotLabel.setText("Selected Spot: ");

        javax.swing.GroupLayout SpotPanelLayout = new javax.swing.GroupLayout(SpotPanel);
        SpotPanel.setLayout(SpotPanelLayout);
        SpotPanelLayout.setHorizontalGroup(
            SpotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        SpotPanelLayout.setVerticalGroup(
            SpotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 124, Short.MAX_VALUE)
        );

        ConfirmButton.setText("Confirm");
        ConfirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmButtonActionPerformed(evt);
            }
        });

        BackButton.setText("Back");
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FloorsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(RowPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SelectedFloorLabel)
                                    .addComponent(SelectedRowLabel)
                                    .addComponent(SelectedSpotLabel))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(SpotPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 166, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(TimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(153, 153, 153))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(BackButton)
                                .addGap(117, 117, 117)
                                .addComponent(ConfirmButton)
                                .addGap(178, 178, 178))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(TimeLabel)
                .addGap(4, 4, 4)
                .addComponent(SelectedFloorLabel)
                .addGap(18, 18, 18)
                .addComponent(FloorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SelectedRowLabel)
                .addGap(18, 18, 18)
                .addComponent(RowPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SelectedSpotLabel)
                .addGap(18, 18, 18)
                .addComponent(SpotPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConfirmButton)
                    .addComponent(BackButton))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ConfirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmButtonActionPerformed
        String Confirmation = "Vehicle: " + type.toString() +
                "\nPlate: " + plate +
                "\nFloor: " + selectedFloor +
                "\nRow: " + selectedRow +
                "\nSpot: " + selectedSpot;
        System.out.println(Confirmation);
        if (reserveTimeStamp != null) {
            System.out.println("Reserve Parking");
            new ParkingSummary(type, plate, selectedFloor, selectedRow, selectedSpot, reserveTimeStamp).setVisible(true);
        } else {
            new ParkingSummary(type, plate, selectedFloor, selectedRow, selectedSpot).setVisible(true); }
        this.dispose();
    }//GEN-LAST:event_ConfirmButtonActionPerformed

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        new LandingPage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ParkingFloorPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ParkingFloorPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ParkingFloorPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ParkingFloorPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ParkingFloorPage(VehicleType.CAR, "TEST123").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    private javax.swing.JButton ConfirmButton;
    private javax.swing.JPanel FloorsPanel;
    private javax.swing.JPanel RowPanel;
    private javax.swing.JLabel SelectedFloorLabel;
    private javax.swing.JLabel SelectedRowLabel;
    private javax.swing.JLabel SelectedSpotLabel;
    private javax.swing.JPanel SpotPanel;
    private javax.swing.JLabel TimeLabel;
    // End of variables declaration//GEN-END:variables
}
