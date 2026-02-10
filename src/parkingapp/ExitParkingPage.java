package parkingapp;

import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class ExitParkingPage extends javax.swing.JFrame {
    private final String plate;
    private PaymentMethod method;
    private JPanel ParkingFeeSummaryButtonPanel;
    private JPanel ParkingPaymentPanel;
    private JPanel CompletedPaymentParkingSummaryPanel;
    private JPanel CompletedPaymentParkingSummaryButtonPanel;
    
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
    
    //Show Parking Fee Summary Before Payment
    private void initParkingFeeSummary() {
        ParkingFeeSummaryPanel.setLayout(new GridLayout(0, 1, 10, 10));
        
        String ticketID = "T-"+ plate + "-Time";
        JLabel TicketIDLabel = new JLabel("TicketID    : " + ticketID);
        TicketIDLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        TicketIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
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
        
        ParkingFeeSummaryPanel.add(TicketIDLabel);
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
    
    //Show Parking Fee Summary Buttons Before Payment
    private void initParkingFeeSummaryButton() {
        ParkingFeeSummaryButtonPanel = new JPanel();
        ParkingFeeSummaryButtonPanel.setLayout(new GridLayout(0, 2, 10, 10));
        JButton backButton = new JButton("Back");
        JButton payButton = new JButton("Pay");

        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        payButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        backButton.addActionListener(e -> {
            new ParkingPage(ParkingPage.Mode.EXIT).setVisible(true);
            dispose();
        });
        payButton.addActionListener(e -> {
            ParkingFeeSummaryPanel.removeAll();
            initParkingPaymentPanel();
            ParkingFeeSummaryPanel.add(ParkingPaymentPanel);
            ParkingFeeSummaryPanel.revalidate(); 
            ParkingFeeSummaryPanel.repaint();
        });

        ParkingFeeSummaryButtonPanel.add(backButton);
        ParkingFeeSummaryButtonPanel.add(payButton);
    }
    
    //Show Payment Tabs
    private void initParkingPaymentPanel() {
        ParkingPaymentPanel = new JPanel(new BorderLayout(10, 10));
        ParkingPaymentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel paymentLabel = new JLabel("Payment Methods", SwingConstants.CENTER);
        paymentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        ParkingPaymentPanel.add(paymentLabel, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tabs.addTab("Card", buildCardPaymentPanel());
        tabs.addTab("Cash", buildCashPaymentPanel());

        ParkingPaymentPanel.add(tabs, BorderLayout.CENTER);
    }
    
    //Show Card Payment Tabs
    private JPanel buildCardPaymentPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Pay by Card");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel hint = new JLabel("Enter your card details below:");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField nameField = new JTextField();
        JTextField numberField = new JTextField();
        JTextField expiryField = new JTextField();
        JPasswordField cvvField = new JPasswordField();

        form.add(new JLabel("Name on Card:"));
        form.add(nameField);

        form.add(new JLabel("Card Number:"));
        form.add(numberField);

        form.add(new JLabel("Expiry (MM/YY):"));
        form.add(expiryField);

        form.add(new JLabel("CVV:"));
        form.add(cvvField);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton payBtn = new JButton("Pay Now");
        JButton backBtn = new JButton("Back");

        btnRow.add(backBtn);
        btnRow.add(payBtn);

        p.add(title);
        p.add(Box.createVerticalStrut(6));
        p.add(hint);
        p.add(Box.createVerticalStrut(12));
        p.add(form);
        p.add(Box.createVerticalStrut(12));
        p.add(btnRow);

        payBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Confirm Payment,\nPayments are not refundable.", "Confirmation Dialog", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.NO_OPTION) {return;}
            method = PaymentMethod.CARD;
            ParkingPaymentPanel.removeAll();
            initCompletedPaymentParkingSummary();
            ParkingPaymentPanel.add(CompletedPaymentParkingSummaryPanel);
            ParkingPaymentPanel.revalidate(); 
            ParkingPaymentPanel.repaint();
        });
        backBtn.addActionListener(e -> {
            new ExitParkingPage(plate).setVisible(true);
            this.dispose();
        });
        return p;
    }
    
    //Show Cash Payment Tabs
    private JPanel buildCashPaymentPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Pay by Cash");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea instructions = new JTextArea(
            "1) Proceed to the cashier machine.\n" +
            "2) Tell them your plate number.\n" +
            "3) Pay the amount shown.\n" +
            "4) Keep the receipt to exit."
        );
        instructions.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        instructions.setEditable(false);
        instructions.setOpaque(false);
        instructions.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton confirmBtn = new JButton("I Paid");
        JButton backBtn = new JButton("Back");

        btnRow.add(backBtn);
        btnRow.add(confirmBtn);

        p.add(title);
        p.add(Box.createVerticalStrut(10));
        p.add(instructions);
        p.add(Box.createVerticalStrut(12));
        p.add(btnRow);

        confirmBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Confirm Payment,\nPayments are not refundable.", "Confirmation Dialog", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.NO_OPTION) {return;}
            method = PaymentMethod.CASH;
            ParkingPaymentPanel.removeAll();
            initCompletedPaymentParkingSummary();
            ParkingPaymentPanel.add(CompletedPaymentParkingSummaryPanel);
            ParkingPaymentPanel.revalidate(); 
            ParkingPaymentPanel.repaint();
        });
        backBtn.addActionListener(e -> {
            new ExitParkingPage(plate).setVisible(true);
            this.dispose();
        });

        return p;
    }
    
     //Show Completed Payment Parking Fee Summary
    private void initCompletedPaymentParkingSummary() {
        CompletedPaymentParkingSummaryPanel = new JPanel();
        CompletedPaymentParkingSummaryPanel.setLayout(new GridLayout(0, 1, 10, 10));
        
        JLabel ThankLabel = new JLabel("Thank You!");
        ThankLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        ThankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        String ticketID = "T-"+ plate + "-Time";
        JLabel TicketIDLabel = new JLabel("TicketID    : " + ticketID);
        TicketIDLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        TicketIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
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
        
        JLabel methodLabel = new JLabel("Payment Method :  " + method);
        methodLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        methodLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        initCompletedPaymentParkingSummaryButton();
        
        CompletedPaymentParkingSummaryPanel.add(ThankLabel);
        CompletedPaymentParkingSummaryPanel.add(TicketIDLabel);
        CompletedPaymentParkingSummaryPanel.add(vehicleTypeLabel);
        CompletedPaymentParkingSummaryPanel.add(plateNumberLabel);
        CompletedPaymentParkingSummaryPanel.add(floorLabel);
        CompletedPaymentParkingSummaryPanel.add(parkingSpotLabel);
        CompletedPaymentParkingSummaryPanel.add(parkingRateLabel);
        
        CompletedPaymentParkingSummaryPanel.add(parkingDurationLabel);
        CompletedPaymentParkingSummaryPanel.add(parkingFeeLabel);
        CompletedPaymentParkingSummaryPanel.add(parkingFineLabel);
        CompletedPaymentParkingSummaryPanel.add(parkingTotalFeeLabel);
        CompletedPaymentParkingSummaryPanel.add(methodLabel);
          
        CompletedPaymentParkingSummaryPanel.add(CompletedPaymentParkingSummaryButtonPanel);
        
        CompletedPaymentParkingSummaryPanel.revalidate();
        CompletedPaymentParkingSummaryPanel.repaint();
    }
    
    private void initCompletedPaymentParkingSummaryButton() {
        CompletedPaymentParkingSummaryButtonPanel = new JPanel();
        CompletedPaymentParkingSummaryButtonPanel.setLayout(new GridLayout(0, 2, 10, 10));
        JButton closeButton = new JButton("Close");
        JButton downloadButton = new JButton("Download");

        closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        downloadButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        closeButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Do you want to go back?\nRemember to download your parking receipt.", "Confirmation Dialog", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.NO_OPTION) {return;}
            new LandingPage().setVisible(true);
            dispose();
        });
        downloadButton.addActionListener(e -> {
            System.out.println("Downloading...");
            new LandingPage().setVisible(true);
            dispose();
        });

        CompletedPaymentParkingSummaryButtonPanel.add(closeButton);
        CompletedPaymentParkingSummaryButtonPanel.add(downloadButton);
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
