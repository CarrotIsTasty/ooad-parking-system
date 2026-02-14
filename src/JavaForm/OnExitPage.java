package JavaForm;

import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import Database.DatabaseManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import parkingapp.Fine;
import parkingapp.Payment;
import parkingapp.PaymentMethod;
import parkingapp.Ticket;
import strategy.FineContext;

public class OnExitPage extends javax.swing.JFrame {
    private final String plate;
    private double payableAmount;
    private PaymentMethod method;
    private Fine fine;
    private double amount;
    private double total;
    private double parkingfee;
    private double calculatedFees;
    //private FineContext fineContext;
    private final DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final DateTimeFormatter timeInHours = DateTimeFormatter.ofPattern("HH:mm");
    private JPanel ParkingFeeSummaryButtonPanel;
    private JPanel ParkingPaymentPanel;
    private JPanel CompletedPaymentParkingSummaryPanel;
    private JPanel CompletedPaymentParkingSummaryButtonPanel;
    
    public OnExitPage(String plate) {
        this.plate = plate;
        initComponents();
        ParkingFeeSummaryButtonPanel = new JPanel();
        initParkingFeeSummary();
        ParkingFeeSummaryPanel.setVisible(true);
        LocalDateTime now = LocalDateTime.now();
        TimeLabel.setText("Time Now: " + now.format(timeFmt));
    }
    
    private void generateReceipt(JButton receiptButton) {
        try {
            LocalDateTime now = LocalDateTime.now();
            String timeString = now.format(timeFmt); 

            String receiptID = "R-" + plate + "-" + timeString.replace(":", "-").replace(" ", "-");
            String fileName = receiptID + ".txt";

            File file = new File(fileName);
            FileWriter writer = new FileWriter(file);

            writer.write("Receipt Time   : " + now.format(timeFmt) + "\n");
            writer.write("Receipt ID     : " + receiptID + "\n");
            writer.write("Plate Number   : " + plate + "\n");

            if (method != null) {
                writer.write("Payment Method : " + this.method + "\n");
            }

            writer.write("Parking Fee    : RM " + String.format("%.2f", this.calculatedFees) + "\n");

            double fineAmt = 0.0;
            if (fine != null) {
             
            }
            writer.write("Fine Amount    : RM " + String.format("%.2f", this.fine) + "\n");

            writer.write("Total Paid     : RM " + String.format("%.2f", this.total) + "\n");
            writer.write("----------------------------------\n");
            writer.write("Thank you! Drive safely.\n");

            writer.close();

            Desktop.getDesktop().open(file);
            JOptionPane.showMessageDialog(this, "Receipt downloaded!");

         
            receiptButton.setEnabled(false);
            receiptButton.setBackground(Color.LIGHT_GRAY);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error, Can't generate receipt file.");
        }
    }

    
    //Show Parking Fee Summary Before Payment
    private void initParkingFeeSummary() {
        ParkingFeeSummaryPanel.setLayout(new GridLayout(0, 1, 10, 10));
        //--- Retrieve TicketID from Ticket
        //--- Retrieve Entry Time, Plate, SpotID from vehicles
        //--- Retrieve Amount from fines
        //if vehicle type is handicapped park at handicapped spot fee = 0
        //park at other spot spot calculate as nomral rate
        //park at reserved spot the rate = 2
        FineContext fineContext = new FineContext();
        DatabaseManager db = DatabaseManager.getInstance();
        Ticket ticket = db.getTicketDetailsByPlateNumber(this.plate);
        this.calculatedFees = ticket.calculateFees();
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter timeInHours = DateTimeFormatter.ofPattern("HH");
        String timeString = now.format(timeFormatter);
        String timeStringHours = now.format(timeInHours);
       
        JLabel TicketIDLabel = new JLabel("TicketID    : " + ticket.getTicketID());
        TicketIDLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        TicketIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel entryExitTimeLabel = new JLabel("Entry Time : " + ticket.getEntryTimeFormat() +  "  Exit Time: " + timeString);
        entryExitTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        entryExitTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel plateNumberLabel = new JLabel("Plate Number  : " + ticket.getVehicle().getPlateNumber());
        plateNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        plateNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel parkingSpotLabel = new JLabel("Parking Spot  :  " + ticket.getSpot().getSpotId());
        parkingSpotLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingSpotLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Can you make my life easy and remove this shit
        
        double hourlyRate;
        
        hourlyRate = switch (db.getSpotTypeByLPlate(this.plate)) {
            case "COMPACT" -> 2.0;
            case "REGULAR" -> 5.0;
            case "Handicapped" -> 2.0;
            case "RESERVED" -> 10.0;
            default -> 5.0;
        };
        
        
        JLabel parkingRateLabel = new JLabel("Parking Rate  :"+ hourlyRate +  " /hour");
        parkingRateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingRateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel parkingDurationLabel = new JLabel("Duration    : " + ticket.getDurationFromNow(now)  + "Hours");
        parkingDurationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingDurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        this.parkingfee = ticket.calculateFees();
        
        JLabel parkingFeeLabel = new JLabel("Parking Fee : RM " + this.calculatedFees);
        parkingFeeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingFeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
//        double fineAmount = 0;
//        if (fine != null)
//            fineAmount = fine.calculateFees();
        
        JLabel parkingFineLabel = new JLabel("Parking Fine : RM "+ fineContext.checkAndCalculateFines(ticket));
        parkingFineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingFineLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
         this.total = ticket.calculateFees() + fineContext.checkAndCalculateFines(ticket);
        
        
        JLabel parkingTotalFeeLabel = new JLabel("Total Fee : RM " + this.total);
        parkingTotalFeeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingTotalFeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        initParkingFeeSummaryButton();
        
        ParkingFeeSummaryPanel.add(TicketIDLabel);
        ParkingFeeSummaryPanel.add(entryExitTimeLabel);
        ParkingFeeSummaryPanel.add(plateNumberLabel);
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
            new OnEntryPage(OnEntryPage.Mode.EXIT).setVisible(true);
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

        JLabel paymentLabel = new JLabel("Payable Amounts: " + this.calculatedFees , SwingConstants.CENTER);
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
        
        
        NumberFormat format = NumberFormat.getNumberInstance(); 
        format.setMinimumFractionDigits(2); 
        format.setMaximumFractionDigits(2);
        
        NumberFormatter formatter = new NumberFormatter(format); 
        formatter.setAllowsInvalid(false); 
        formatter.setMinimum(0.00);

        JFormattedTextField paymentField = new JFormattedTextField();

        form.add(new JLabel("Name on Card:"));
        form.add(nameField);

        form.add(new JLabel("Card Number:"));
        form.add(numberField);

        form.add(new JLabel("Expiry (MM/YY):"));
        form.add(expiryField);

        form.add(new JLabel("CVV:"));
        form.add(cvvField);
        
        form.add(new JLabel("Amount Pay: RM"));
        form.add(paymentField);

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
            //Check Payments
            String text = paymentField.getText().trim();
            if ( Double.parseDouble(text) != calculatedFees) {
                JOptionPane.showMessageDialog(p, "Please pay exact amount.");
                return;
            }
            if (text.isEmpty()) {JOptionPane.showMessageDialog(p, "Please enter an amount to pay."); return;
            } else {
                try {
                     this.amount = Double.parseDouble(text);
                    if (this.amount < 0) { JOptionPane.showMessageDialog(p, "Amount must be greater than 0.00");  return;} 
                    else { JOptionPane.showMessageDialog(p, "Processing payment of " + format.format(this.amount));}
                } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(p, "Invalid amount format. Please use 0.00");  return;}
            }
            
            int result = JOptionPane.showConfirmDialog(this, "Confirm Payment,\nPayments are not refundable.", "Confirmation Dialog", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.NO_OPTION) {return;}
            method = PaymentMethod.CARD;
            //amount = paymentField.getText();
            //JEVAAN's PART
            
            //if vehicle type is handicapped park at handicapped spot fee = 0
            //park at other spot spot calculate as nomral rate
            //park at reserved spot the rate = 2
            /*DatabaseManager db = DatabaseManager.getInstance();
            Ticket ticket = db.getTicketDetailsByPlateNumber(plate);
            FineContext finecontext = new FineContext();
            
            double total = ticket.calculateFees() + fine.Context.checkAndCalculateFines(ticket);
            double finalFee = total;
            
            if (spot_type == spot_type.HANDICAPPED){
                if (vehicle_type == vehicle_type.HANDICAPPED){
                    finalFee = 0;
                }else{
                    finalFee = total;
                }
            } else if (spot_type == spot_type.RESERVED){
                finalFee = hours_parked * 2.0;
            }else {
                finalFee = total; // normal rate
            } // not completed yet */
            
            
            //payableAmount = (double)paymentField.getValue(); 
            //if payableAmount >= parkingFee then start saving else inform user please pay full amount parking fee
            //----- Save Start Here -----//
            //Save payment to db plate, amount, parkingfee, fineamount, paymentmethod, payment time, ticketid
            LocalDateTime now = LocalDateTime.now();
            String exitTime = now.format(timeFmt);
            System.out.println("Updating into DB...\nVehicle DB: " + plate + ", " + exitTime);
            //Set parkingspot isavailable = 1, current vehicle = null, entrytime = null where ID = FX-RX-SX
            //----- Save End Here -----//
           
            DatabaseManager db = DatabaseManager.getInstance();
            Ticket ticket = db.getTicketDetailsByPlateNumber(plate);
            //db.clearParkingSpot(ticket.getSpot().getSpotId(), plate, ticket);
            
            ParkingPaymentPanel.removeAll();
            initCompletedPaymentParkingSummary();
            ParkingPaymentPanel.add(CompletedPaymentParkingSummaryPanel);
            ParkingPaymentPanel.revalidate(); 
            ParkingPaymentPanel.repaint();
        });
        backBtn.addActionListener(e -> {
            new OnExitPage(plate).setVisible(true);
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

        JTextArea instructions = new JTextArea("Please enter the payable cash amount");
 
        instructions.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        instructions.setEditable(false);
        instructions.setOpaque(false);
        instructions.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);

        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(false);
        formatter.setMinimum(0.00);
        JFormattedTextField paymentField = new JFormattedTextField();
        paymentField.setPreferredSize(new Dimension(200, 25));
        paymentField.setMaximumSize(new Dimension(200, 25));
        JPanel amountRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        amountRow.add(new JLabel("Amount Pay: RM" + this.total)); 
        amountRow.add(paymentField);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton confirmBtn = new JButton("Pay");
        JButton backBtn = new JButton("Back");

        btnRow.add(backBtn);
        btnRow.add(confirmBtn);

        p.add(title);
        p.add(Box.createVerticalStrut(10));
        p.add(instructions);
        p.add(amountRow); 
        amountRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(Box.createVerticalStrut(12));
        p.add(btnRow);

        confirmBtn.addActionListener(e -> {
            String text = paymentField.getText().trim();
            if (text.isEmpty()) {JOptionPane.showMessageDialog(p, "Please enter an amount to pay."); return;
            } else {
                try {
                     this.amount = Double.parseDouble(text);
                    if (this.amount <= 0) { JOptionPane.showMessageDialog(p, "Amount must be greater than 0.00"); return;} 
                    else { JOptionPane.showMessageDialog(p, "Processing payment of " + format.format(this.amount));}
                } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(p, "Invalid amount format. Please use 0.00"); return;}
            }
            
            int result = JOptionPane.showConfirmDialog(this, "Confirm Payment,\nPayments are not refundable.", "Confirmation Dialog", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.NO_OPTION) {return;}
            method = PaymentMethod.CASH;
            String amount = paymentField.getText();
            //payableAmount = (double)paymentField.getValue();
            //----- Save Start Here -----//
            
            LocalDateTime now = LocalDateTime.now();
            String exitTime = now.format(timeFmt);
            System.out.println("Updating into DB...\nVehicle DB: " + plate + ", " + exitTime);
            //Set parkingspot isavailable = 1, current vehicle = null, entrytime = null where ID = FX-RX-SX
            //----- Save End Here -----//
            ParkingPaymentPanel.removeAll();
            initCompletedPaymentParkingSummary();
            ParkingPaymentPanel.add(CompletedPaymentParkingSummaryPanel);
            ParkingPaymentPanel.revalidate(); 
            ParkingPaymentPanel.repaint();
        });
        backBtn.addActionListener(e -> {
            new OnExitPage(plate).setVisible(true);
            this.dispose();
        });

        return p;
    }
    
     //Show Completed Payment Parking Fee Summary
    private void initCompletedPaymentParkingSummary() {
        CompletedPaymentParkingSummaryPanel = new JPanel();
        CompletedPaymentParkingSummaryPanel.setLayout(new GridLayout(0, 1, 10, 10));
        DatabaseManager db = DatabaseManager.getInstance();
        Ticket ticket = db.getTicketDetailsByPlateNumber(plate);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter timeInHours = DateTimeFormatter.ofPattern("HH");
        String timeString = now.format(timeFormatter);
        String timeStringHours = now.format(timeInHours);
        FineContext fineContext = new FineContext();
        
        db.clearParkingSpot(ticket.getSpot().getSpotId(), plate, ticket);
        
        
        JLabel ThankLabel = new JLabel("Thank You!");
        ThankLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        ThankLabel.setHorizontalAlignment(SwingConstants.CENTER);
      
        String ticketID = "T-"+ plate + "-Time";
        JLabel TicketIDLabel = new JLabel("TicketID    : " + ticket.getTicketID());
        TicketIDLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        TicketIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel entryExitTimeLabel = new JLabel("Entry Time :" + ticket.getEntryTimeFormat() + " Exit Time: " +  timeString);
        entryExitTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        entryExitTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel plateNumberLabel = new JLabel("Plate Number  : " + plate);
        plateNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        plateNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel parkingSpotLabel = new JLabel("Parking Spot  : " + ticket.getSpot().getSpotId());
        parkingSpotLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingSpotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel parkingDurationLabel = new JLabel("Duration    :" + ticket.getDuration() +  " Hours");
        parkingDurationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingDurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel parkingFeeLabel = new JLabel("Parking Fee : RM " + this.parkingfee);
        parkingFeeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingFeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel parkingFineLabel = new JLabel("Parking Fine : RM " + fineContext.checkAndCalculateFines(ticket));
        parkingFineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingFineLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel parkingTotalPaidLabel = new JLabel("Total Amount Paid : RM " + this.amount );
        parkingTotalPaidLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        parkingTotalPaidLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel methodLabel = new JLabel("Payment Method :  " + method);
        methodLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        methodLabel.setHorizontalAlignment(SwingConstants.CENTER);
       
        this.total = this.total - this.amount;
        
        JLabel remainingBalanceLabel = new JLabel("Remaining Balance :  RM " + this.total);
        remainingBalanceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        remainingBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        Payment payment = new Payment(this.amount, this.parkingfee, fineContext.checkAndCalculateFines(ticket), ticket.getTicketID(), plate, method );
        payment.setFine();
        payment.saveInDb();
     
        
        
        initCompletedPaymentParkingSummaryButton();
        
        CompletedPaymentParkingSummaryPanel.add(ThankLabel);
        CompletedPaymentParkingSummaryPanel.add(TicketIDLabel);
        CompletedPaymentParkingSummaryPanel.add(entryExitTimeLabel);
        CompletedPaymentParkingSummaryPanel.add(plateNumberLabel);
        CompletedPaymentParkingSummaryPanel.add(parkingSpotLabel);
        
        CompletedPaymentParkingSummaryPanel.add(parkingDurationLabel);
        CompletedPaymentParkingSummaryPanel.add(parkingFeeLabel);
        CompletedPaymentParkingSummaryPanel.add(parkingFineLabel);
        CompletedPaymentParkingSummaryPanel.add(parkingTotalPaidLabel);
        CompletedPaymentParkingSummaryPanel.add(remainingBalanceLabel);
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
            new StartPage().setVisible(true);
            dispose();
        });
        downloadButton.addActionListener(e -> {
            System.out.println("Downloading...");
            //generateReceipt(); to T-PLATE-TIME.txt 
            // JASON's Part
            new StartPage().setVisible(true);
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
            .addGap(0, 610, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
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
                new OnExitPage("TEST123").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ParkingFeeSummaryPanel;
    private javax.swing.JLabel TimeLabel;
    // End of variables declaration//GEN-END:variables
}
