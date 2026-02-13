/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import Config.FileConfigurationService;
import Database.DatabaseConnection;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import strategy.FineContext;

/**
 *
 * @author herbert
 */
public class AdminDashboardGUI extends JFrame {

    private JLabel totalUnpaidLabel;
    private JLabel avgFineLabel;
    private Connection connection;
    private String adminName;
    private FileConfigurationService config;
    private String schemeChosen;
    private JTabbedPane tabbedPane;
    private JTable overViewTable, currentVehicleTable, unpaidFinesTable, fineScheme;
    private DefaultTableModel overViewModel, currentVehicleModel, unpaidFinesModel, fineSchemeModel;
    FineContext fineContext = new FineContext();

    public AdminDashboardGUI() {
        initComponents();
        loadInitialData();

    }

    public AdminDashboardGUI(Connection connection, String adminName) {
        this.connection = connection;
        this.adminName = adminName;
        this.config = FileConfigurationService.getInstance("./config.ini", false);

        try {
            boolean alreadyInitialized = this.config.load();
            if (!alreadyInitialized) {
                this.config.set("fineStrategy", "FIXED");
                this.config.save();
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        initComponents();
        loadInitialData();
    }

    private void initComponents() {
        setTitle("Parking App - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //  Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(204, 0, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblWelcome = new JLabel("Welcome, Administrator");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 18));
        lblWelcome.setForeground(Color.WHITE);

        JLabel lblDateTime = new JLabel(LocalDateTime.now().toString());
        lblDateTime.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDateTime.setForeground(Color.WHITE);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setForeground(new Color(204, 0, 0));
        btnLogout.addActionListener(e -> logout());

        topPanel.add(lblWelcome, BorderLayout.WEST);
        topPanel.add(lblDateTime, BorderLayout.CENTER);
        topPanel.add(btnLogout, BorderLayout.EAST);

        //Tabbed Pane
        tabbedPane = new JTabbedPane();

        //Show data like Occupancy Rate, Revenue, Spot Counts
        tabbedPane.addTab("👤 Overview", new OverviewPanel(connection));
        //Plate number, vehicle type,spot ID, Floor, Entry Time, Duration
        tabbedPane.addTab(" Current Vehicles", createCurrentVehiclesPanel());
        //Summary Panel,Fines Table, Fine ID, Plate num, Amount, Reason, Issue Date
        tabbedPane.addTab("💬 Unpaid Fines", createUnpaidFinesPanel());
        //current scheme, scheme option, scheme details, apply button to change scheme
        tabbedPane.addTab("📅 Fine Scheme", createFineSchemePanel());

        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        //I hope you're okay with the window properties being like this one
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    //TODO: Need to implement createOverviewPanel. I think a class is good
    private JPanel createCurrentVehiclesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnRefresh = new JButton("🔄 Refresh");
        btnRefresh.addActionListener(e -> loadVehicles());
        controlPanel.add(btnRefresh);

        String[] columns = {"Plate Number", "Vehicle Type", "Spot ID", "Floor", "Entry Time", "Duration", "Created"};
        currentVehicleModel = new DefaultTableModel(columns, 0) {
        };
        currentVehicleTable = new JTable(currentVehicleModel);
        currentVehicleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        currentVehicleTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(currentVehicleTable);

        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;

    }
    // this is for the refresh button in createCurrentVehiclesPanel

    private void loadVehicles() {
        currentVehicleModel.setRowCount(0);

        String sql = """
        SELECT 
            v.license_plate,
            v.vehicle_type,
            ps.spot_id,
            ps.floor_number,
            STRFTIME('%H:%M:%S', v.entry_time / 1000, 'unixepoch') as entry_time,
            ROUND((STRFTIME('%s', 'now') * 1000 - v.entry_time) / 360000.0, 1) as hours_parked,
            t.created_at
        FROM vehicles v
        LEFT JOIN parking_spots ps ON v.spot_id = ps.spot_id
        LEFT JOIN tickets t ON v.license_plate = t.license_plate 
            AND t.exit_time IS NULL
        WHERE v.exit_time IS NULL
        ORDER BY v.entry_time DESC
        """;

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String plateNumber = rs.getString("license_plate");
                String vehicleType = rs.getString("vehicle_type");
                String spotId = rs.getString("spot_id");
                int floorNumber = rs.getInt("floor_number");
                String entryTime = rs.getString("entry_time");
                double hoursParked = rs.getDouble("hours_parked");
                String createdAt = rs.getString("created_at");

                Object[] row = {
                    plateNumber,
                    vehicleType,
                    spotId,
                    floorNumber,
                    formatDateTime(entryTime),
                    formatDuration(hoursParked),
                    formatDateTime(createdAt)
                };

                currentVehicleModel.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading vehicles: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to format datetime
    private String formatDateTime(String dateTimeStr) {
        if (dateTimeStr == null) {
            return "";
        }
        try {
            // Parse the ISO datetime format from SQLite
            // Example: 2024-01-15 14:30:25
            String[] parts = dateTimeStr.split(" ");
            if (parts.length == 2) {
                String date = parts[0];
                String time = parts[1].substring(0, 5); // Get HH:MM only
                return date + " " + time;
            }
            return dateTimeStr;
        } catch (Exception e) {
            return dateTimeStr;
        }
    }

    private String formatDuration(double hours) {
        if (hours < 1) {
            int minutes = (int) (hours * 60);
            return minutes + " min" + (minutes != 1 ? "s" : "");
        } else if (hours < 24) {
            return String.format("%.1f hours", hours);
        } else {
            int days = (int) (hours / 24);
            int remainingHours = (int) (hours % 24);
            return days + " day" + (days != 1 ? "s" : "")
                    + (remainingHours > 0 ? " " + remainingHours + "h" : "");
        }
    }

    private JPanel createUnpaidFinesPanel() {
        JPanel finesPanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        summaryPanel.setBorder(new TitledBorder("Fines Summary"));

        // Total Unpaid Panel
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBorder(BorderFactory.createEtchedBorder());
        totalPanel.add(new JLabel("Total Unpaid", SwingConstants.CENTER), BorderLayout.NORTH);
        totalUnpaidLabel = new JLabel("RM 0.00", SwingConstants.CENTER);
        totalUnpaidLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalUnpaidLabel.setForeground(new Color(204, 0, 0)); // Red color for better visibility
        totalPanel.add(totalUnpaidLabel, BorderLayout.CENTER);
        summaryPanel.add(totalPanel);

        // Average Fine Panel
        JPanel avgPanel = new JPanel(new BorderLayout());
        avgPanel.setBorder(BorderFactory.createEtchedBorder());
        avgPanel.add(new JLabel("Average Fine", SwingConstants.CENTER), BorderLayout.NORTH);
        avgFineLabel = new JLabel("RM 0.00", SwingConstants.CENTER);
        avgFineLabel.setFont(new Font("Arial", Font.BOLD, 20));
        avgFineLabel.setForeground(new Color(0, 100, 200)); // Blue color
        avgPanel.add(avgFineLabel, BorderLayout.CENTER);
        summaryPanel.add(avgPanel);

        // Count Panel (add this for better summary)
        JPanel countPanel = new JPanel(new BorderLayout());
        countPanel.setBorder(BorderFactory.createEtchedBorder());
        countPanel.add(new JLabel("Unpaid Count", SwingConstants.CENTER), BorderLayout.NORTH);
        JLabel countLabel = new JLabel("0", SwingConstants.CENTER);
        countLabel.setFont(new Font("Arial", Font.BOLD, 20));
        countLabel.setForeground(new Color(0, 150, 0)); // Green color
        countPanel.add(countLabel, BorderLayout.CENTER);
        summaryPanel.add(countPanel);

        finesPanel.add(summaryPanel, BorderLayout.NORTH);

        JButton btnRefresh = new JButton("🔄 Refresh");
        btnRefresh.addActionListener(e -> loadFines());
        controlPanel.add(btnRefresh);

        // Add Mark as Paid button
        JButton btnMarkPaid = new JButton("✓ Mark as Paid");
        btnMarkPaid.setBackground(new Color(0, 150, 0));
        btnMarkPaid.setForeground(Color.WHITE);
        btnMarkPaid.addActionListener(e -> markFineAsPaid());
        controlPanel.add(btnMarkPaid);
//
        String[] columns = {"Fine ID", "License Plate", "Amount","Payment", "Issue Date"};

        unpaidFinesModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        unpaidFinesTable = new JTable(unpaidFinesModel);
        unpaidFinesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        unpaidFinesTable.setRowHeight(25);

        // Add right-click menu for fines
        JPopupMenu finePopupMenu = new JPopupMenu();
        JMenuItem markPaidItem = new JMenuItem("Mark as Paid");
        markPaidItem.addActionListener(e -> markFineAsPaid());
        finePopupMenu.add(markPaidItem);
        unpaidFinesTable.setComponentPopupMenu(finePopupMenu);

        JScrollPane scrollPane = new JScrollPane(unpaidFinesTable);

        finesPanel.add(controlPanel, BorderLayout.NORTH);
        finesPanel.add(scrollPane, BorderLayout.CENTER);

        return finesPanel;
    }

    private void loadFines() {
    // Clear existing rows
    unpaidFinesModel.setRowCount(0);

    String sql = """
        SELECT 
            fine_id,
            license_plate,
            amount,
            is_paid,
            issue_date
        FROM fines 
        WHERE is_paid = 0 
        ORDER BY issue_date DESC
        """;

    double totalUnpaid = 0;
    int fineCount = 0;

    // Use this.connection instead of creating a new connection
    try (PreparedStatement pstmt = this.connection.prepareStatement(sql); 
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            int fineId = rs.getInt("fine_id");
            String licensePlate = rs.getString("license_plate");
            double amount = rs.getDouble("amount");
            boolean isPaid = rs.getBoolean("is_paid");
            String issueDate = rs.getString("issue_date");

            totalUnpaid += amount;
            fineCount++;

            Object[] row = {
                fineId,
                licensePlate,
                String.format("RM %.2f", amount),
                isPaid ? "Paid" : "Unpaid",  // Display payment status
                formatDateTime(issueDate)
            };

            unpaidFinesModel.addRow(row);
        }

            // Update summary labels
            totalUnpaidLabel.setText(String.format("RM %.2f", totalUnpaid));
            avgFineLabel.setText(String.format("RM %.2f", fineCount > 0 ? totalUnpaid / fineCount : 0));

            // Update count label
            JPanel finesPanel = (JPanel) tabbedPane.getComponentAt(2);
            JPanel summaryPanel = (JPanel) finesPanel.getComponent(0);
            JPanel countPanel = (JPanel) summaryPanel.getComponent(2);
            JLabel countLabel = (JLabel) countPanel.getComponent(1);
            countLabel.setText(String.valueOf(fineCount));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading fines: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void markFineAsPaid() {
        int selectedRow = unpaidFinesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a fine to mark as paid.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int fineId = (int) unpaidFinesModel.getValueAt(selectedRow, 0);
        String licensePlate = (String) unpaidFinesModel.getValueAt(selectedRow, 1);
        String amount = (String) unpaidFinesModel.getValueAt(selectedRow, 2);

        int confirm = JOptionPane.showConfirmDialog(this,
                String.format("Mark fine #%d (License: %s, Amount: %s) as paid?",
                        fineId, licensePlate, amount),
                "Confirm Payment",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String sql = """
            UPDATE fines 
            SET is_paid = 1, payment_date = CURRENT_TIMESTAMP 
            WHERE fine_id = ?
            """;

            try {
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1, fineId);
                int affected = pstmt.executeUpdate();

                if (affected > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Fine marked as paid successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadFines(); // Refresh the table
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error updating fine: " + e.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateFinesSummary(double totalUnpaid, int fineCount) {
        // Find the summary panel in the fines panel
        // Since we need to update the labels, we need to access them
        // Let's modify the createUnpaidFinesPanel to store references

        JPanel finesPanel = (JPanel) tabbedPane.getComponentAt(2); // Unpaid Fines tab index
        JPanel summaryPanel = (JPanel) finesPanel.getComponent(0); // First component is summaryPanel

        if (summaryPanel instanceof JPanel) {
            // Update Total Panel
            JPanel totalPanel = (JPanel) summaryPanel.getComponent(0);
            JLabel totalLabel = (JLabel) totalPanel.getComponent(1);
            totalLabel.setText(String.format("RM %.2f", totalUnpaid));

            // Update Average Panel
            JPanel avgPanel = (JPanel) summaryPanel.getComponent(1);
            JLabel avgLabel = (JLabel) avgPanel.getComponent(1);
            double avgFine = fineCount > 0 ? totalUnpaid / fineCount : 0;
            avgLabel.setText(String.format("RM %.2f", avgFine));
        }
    }

    private JPanel createFineSchemePanel() {
        JPanel schemePanel = new JPanel(new BorderLayout(10, 10));
        schemePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Current Active Scheme:"), gbc);

        gbc.gridx = 1;
        JLabel currentLabel = new JLabel(fineContext.getSchemeName());
        currentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        currentLabel.setForeground(Color.BLUE);
        centerPanel.add(currentLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(new JLabel("Select New Scheme:"), gbc);

        gbc.gridx = 1;
        JComboBox<String> schemeCombo = new JComboBox<>(new String[]{
            "Fixed Fine Scheme - RM 50 flat for overstaying >24h",
            "Progressive Fine Scheme - Increases with time",
            "Hourly Fine Scheme - RM 20 per hour for overstaying"
        });
        schemeCombo.setPreferredSize(new Dimension(400, 30));
        centerPanel.add(schemeCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JTextArea detailsArea = new JTextArea(6, 50);
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setText("Fixed Fine Scheme:\n• Flat RM 50 fine for vehicles staying more than 24 hours\n• Simple and predictable\n• Applied only if vehicle stays beyond 24 hours");
        detailsArea.setBorder(new TitledBorder("Scheme Details"));
        centerPanel.add(new JScrollPane(detailsArea), gbc);

        schemeCombo.addActionListener(e -> {
            int index = schemeCombo.getSelectedIndex();
            switch (index) {
                case 0:
                    detailsArea.setText("Fixed Fine :\n• Flat RM 50 fine for vehicles staying more than 24 hours\n• Simple and predictable\n• Example: 25 hours = RM 50 fine");
                    schemeChosen = "FIXED";
                    break;
                case 1:
                    detailsArea.setText("Progressive Fine :\n• 0-24 hours: No fine\n• 24-48 hours: RM 50 fine\n• 48-72 hours: Additional RM 100\n• Above 72 hours: Additional RM 200\n• Increases with overstay duration");
                    schemeChosen = "PROGRESSIVE";
                    break;
                case 2:
                    detailsArea.setText("Hourly Fine :\n• RM 20 per hour for overstaying beyond 24 hours\n• Calculated per hour of overstay\n• Example: 25 hours = RM 20 (1 hour overstay)\n• 30 hours = RM 120 (6 hours overstay)");
                    schemeChosen = "HOURLY";
                    break;
            }
        });

        schemePanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton applyButton = new JButton("Apply New Fine Scheme (For Future Entries Only)");
        applyButton.setFont(new Font("Arial", Font.BOLD, 14));
        applyButton.setBackground(new Color(0, 100, 0));
        applyButton.setForeground(Color.WHITE);
        applyButton.addActionListener(e -> {
            String selected = schemeCombo.getSelectedItem().toString();
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Apply " + selected + "?\n\nAre you sure?",
                    "Confirm Scheme Change", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {

                this.fineContext.setStrategyByName(schemeChosen);

                JOptionPane.showMessageDialog(this,
                        "Fine scheme updated successfully!\nNew scheme: " + selected,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                currentLabel.setText(fineContext.getSchemeName());
            }
        });
        buttonPanel.add(applyButton);

        schemePanel.add(buttonPanel, BorderLayout.SOUTH);

        return schemePanel;
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new AdminLoginGUI();
        }
    }

    private void loadInitialData() {
        loadVehicles();
        loadFines();
    }

}
