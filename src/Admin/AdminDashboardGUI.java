/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;
import Admin.Panels.OverviewPanel;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import javax.swing.border.TitledBorder;

/**
 *
 * @author herbert
 */
public class AdminDashboardGUI extends JFrame{
    private Connection connection;
    private String adminName;
    
    private JTabbedPane tabbedPane;
    private JTable overViewTable, currentVehicleTable, unpaidFinesTable,fineScheme;
    private DefaultTableModel overViewModel, currentVehicleModel, unpaidFinesModel, fineSchemeModel;
    
    public AdminDashboardGUI(){
        initComponents();
        loadInitialData();
    }
    
    public AdminDashboardGUI(Connection connection, String adminName) {
        this.connection = connection;
        this.adminName = adminName;
        
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
        tabbedPane.addTab("👤 Overview",new OverviewPanel(connection));
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
        
        
        private JPanel createCurrentVehiclesPanel(){
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
        private void loadVehicles(){
        }
        
        
        
        private JPanel createUnpaidFinesPanel(){
            JPanel finesPanel = new JPanel(new BorderLayout());
            JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            
            JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 10));
            summaryPanel.setBorder(new TitledBorder("Fines Summary"));

            JPanel totalPanel = new JPanel(new BorderLayout());
            totalPanel.setBorder(BorderFactory.createEtchedBorder());
            totalPanel.add(new JLabel("Total Unpaid", SwingConstants.CENTER), BorderLayout.NORTH);
            JLabel totalLabel = new JLabel("RM 450.00", SwingConstants.CENTER);
            totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
            totalLabel.setForeground(Color.CYAN);
            totalPanel.add(totalLabel, BorderLayout.CENTER);
            summaryPanel.add(totalPanel);

            //Im trying to figure out the GUI right now
            JPanel avgPanel = new JPanel(new BorderLayout());
            avgPanel.setBorder(BorderFactory.createEtchedBorder());
            avgPanel.add(new JLabel("Average Fine", SwingConstants.CENTER), BorderLayout.NORTH);
            JLabel avgLabel = new JLabel("RM 90.00", SwingConstants.CENTER);
            avgLabel.setFont(new Font("Arial", Font.BOLD, 20));
            avgPanel.add(avgLabel, BorderLayout.CENTER);
            summaryPanel.add(avgPanel);

            finesPanel.add(summaryPanel, BorderLayout.NORTH);
            
            JButton btnRefresh = new JButton("🔄 Refresh");
            btnRefresh.addActionListener(e -> loadFines());
            controlPanel.add(btnRefresh);

            String[] columns = {"Fine ID", "License Plate", "Amount", "Reason", "Issue Date"};
            
            unpaidFinesModel = new DefaultTableModel(columns,0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
         };
            
            unpaidFinesTable = new JTable(unpaidFinesModel);
            unpaidFinesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            unpaidFinesTable.setRowHeight(25);

            JScrollPane scrollPane = new JScrollPane(unpaidFinesTable);

            finesPanel.add(controlPanel, BorderLayout.NORTH);
            finesPanel.add(scrollPane, BorderLayout.CENTER);
            
                     return finesPanel;
                }
        
        private void loadFines(){
            
        }
        
        
        private JPanel createFineSchemePanel(){
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            
            return panel;
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