package parkingapp;

import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class ParkingSummary extends javax.swing.JFrame {
    private final DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
    private final VehicleType type;
    private final String plate;
    private final int selectedFloor;
    private final int selectedRow;
    private final int selectedSpot;
    private final String reserveTimeStamp;
    private JPanel ParkingSummaryButtonPanel;
    private JPanel ParkingSummaryTicketPanel;
    private JPanel ParkingSummaryTicketButtonPanel;
    
    public ParkingSummary(VehicleType type, String plate, int selectedFloor, int selectedRow, int selectedSpot){
        this(type, plate, selectedFloor, selectedRow, selectedSpot, null);
    }
    
    public ParkingSummary(VehicleType type, String plate, int selectedFloor, int selectedRow, int selectedSpot, String reserveTimeStamp) {
        this.reserveTimeStamp = reserveTimeStamp;
        this.type = type;
        this.plate = plate;
        this.selectedFloor = selectedFloor;
        this.selectedRow = selectedRow;
        this.selectedSpot = selectedSpot;
        System.out.println("Summary"+ reserveTimeStamp);
        initComponents();
        ParkingSummaryButtonPanel = new JPanel();
        ParkingSummaryTicketPanel = new JPanel();
        ParkingSummaryTicketButtonPanel = new JPanel();
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        TimeLabel.setText("Time Now: " + now.format(formatter));
        initParkingSummary();
        initParkingSummaryTicket();
        
        ParkingSummaryPanel.setVisible(true);     
    }
    
    private boolean isReserveMode(){
        return reserveTimeStamp != null;
    }
    
    public void initParkingSummary(){
        ParkingSummaryPanel.setLayout(new GridLayout(0, 1, 10, 10));
        JLabel VehicleTypeLabel = new JLabel("Vehicle Type    : " + type);
        VehicleTypeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        VehicleTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel PlateNumberLabel = new JLabel("Plate Number  : " + plate);
        PlateNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        PlateNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel FloorLabel = new JLabel("Floor Number : " + selectedFloor);
        FloorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        FloorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel ParkingSpotLabel = new JLabel("Parking Spot  : F" + selectedFloor + "-R" + selectedRow+ "-S" + selectedSpot);
        ParkingSpotLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        ParkingSpotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel ParkingRateLabel = new JLabel("Parking Rate  : " + "/hour");
        ParkingRateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        ParkingRateLabel.setHorizontalAlignment(SwingConstants.CENTER);  
        initParkingSummaryButton();
        
        ParkingSummaryPanel.add(VehicleTypeLabel);
        ParkingSummaryPanel.add(PlateNumberLabel);
        ParkingSummaryPanel.add(FloorLabel);
        ParkingSummaryPanel.add(ParkingSpotLabel);
        ParkingSummaryPanel.add(ParkingRateLabel);
        
        if (isReserveMode()) {
            JLabel ReserverTimpStampLabel = new JLabel("Reserved Time    : " + reserveTimeStamp);
            ReserverTimpStampLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            ReserverTimpStampLabel.setHorizontalAlignment(SwingConstants.CENTER);
            ParkingSummaryPanel.add(ReserverTimpStampLabel);
        }
        
        ParkingSummaryPanel.add(ParkingSummaryButtonPanel);
        
        ParkingSummaryPanel.revalidate();
        ParkingSummaryPanel.repaint();
    }
    
    public void initParkingSummaryButton(){
        ParkingSummaryButtonPanel.setLayout(new GridLayout(0, 2, 10, 10));
        JButton backButton = new JButton("Back");
        JButton confirmButton = new JButton("Confirm");

        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        backButton.addActionListener(e -> {
            System.out.println("back button " + reserveTimeStamp);
            if (isReserveMode()){
                new ParkingFloorPage(type, plate, reserveTimeStamp).setVisible(true);
            } else {
                new ParkingFloorPage(type, plate).setVisible(true);
            }
            dispose();
        });
        confirmButton.addActionListener(e -> {
            ParkingSummaryPanel.removeAll(); 
            initParkingSummaryTicket();
            ParkingSummaryPanel.add(ParkingSummaryTicketPanel); 
            ParkingSummaryPanel.revalidate(); 
            ParkingSummaryPanel.repaint();
        });
        
        ParkingSummaryButtonPanel.add(backButton);
        ParkingSummaryButtonPanel.add(confirmButton);
    }
    
    public void initParkingSummaryTicket(){
        ParkingSummaryTicketPanel.removeAll(); 
        ParkingSummaryTicketPanel.setLayout(new GridLayout(0, 1, 10, 10));
        String ticketID = "T-"+ plate + "-Time";
        JLabel TicketIDLabel = new JLabel("TicketID    : " + ticketID);
        TicketIDLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        TicketIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel VehicleTypeLabel = new JLabel("Vehicle Type    : " + type);
        VehicleTypeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        VehicleTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel PlateNumberLabel = new JLabel("Plate Number  : " + plate);
        PlateNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        PlateNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel FloorLabel = new JLabel("Floor Number : " + selectedFloor);
        FloorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        FloorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel ParkingSpotLabel = new JLabel("Parking Spot  : F" + selectedFloor + "-R" + selectedRow+ "-S" + selectedSpot);
        ParkingSpotLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        ParkingSpotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel ParkingRateLabel = new JLabel("Parking Rate  : " + "/hour");
        ParkingRateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        ParkingRateLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        initParkingSummaryTicketButton();
        
        ParkingSummaryTicketPanel.add(TicketIDLabel);
        ParkingSummaryTicketPanel.add(VehicleTypeLabel);
        ParkingSummaryTicketPanel.add(PlateNumberLabel);
        ParkingSummaryTicketPanel.add(FloorLabel);
        ParkingSummaryTicketPanel.add(ParkingSpotLabel);
        ParkingSummaryTicketPanel.add(ParkingRateLabel);
        
        if (isReserveMode()) {
            JLabel ReserverTimpStampLabel = new JLabel("Reserved Time    : " + reserveTimeStamp);
            ReserverTimpStampLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            ReserverTimpStampLabel.setHorizontalAlignment(SwingConstants.CENTER);
            ParkingSummaryTicketPanel.add(ReserverTimpStampLabel);
        }
        
        ParkingSummaryTicketPanel.add(ParkingSummaryTicketButtonPanel);
        
        ParkingSummaryTicketPanel.revalidate();
        ParkingSummaryTicketPanel.repaint();
    }
    
    public void initParkingSummaryTicketButton(){
        ParkingSummaryTicketButtonPanel.removeAll();
        ParkingSummaryTicketButtonPanel.setLayout(new GridLayout(0, 2, 10, 10));
        JButton backButton = new JButton("Exit");
        JButton confirmButton = new JButton("Download");

        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        backButton.addActionListener(e -> {new LandingPage().setVisible(true); dispose();});
        confirmButton.addActionListener(e -> {System.out.println("Downloading Ticket..."); new LandingPage().setVisible(true);dispose();});
        
        ParkingSummaryTicketButtonPanel.add(backButton);
        ParkingSummaryTicketButtonPanel.add(confirmButton);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TimeLabel = new javax.swing.JLabel();
        ParkingSummaryPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TimeLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        TimeLabel.setText("Time: ");

        javax.swing.GroupLayout ParkingSummaryPanelLayout = new javax.swing.GroupLayout(ParkingSummaryPanel);
        ParkingSummaryPanel.setLayout(ParkingSummaryPanelLayout);
        ParkingSummaryPanelLayout.setHorizontalGroup(
            ParkingSummaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        ParkingSummaryPanelLayout.setVerticalGroup(
            ParkingSummaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 369, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(180, Short.MAX_VALUE)
                .addComponent(TimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ParkingSummaryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(TimeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ParkingSummaryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(224, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(ParkingSummary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ParkingSummary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ParkingSummary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ParkingSummary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ParkingSummary(VehicleType.SEDAN, "TEST123", 1, 1, 1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ParkingSummaryPanel;
    private javax.swing.JLabel TimeLabel;
    // End of variables declaration//GEN-END:variables
}
