package Admin;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class AdminLoginGUI extends JFrame {
    private JTextField txtAdminName;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private Connection connection;
    
    public AdminLoginGUI() {
        initComponents();
        connectToDatabase();
    }
    
    private void connectToDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:parking_app.db");
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Database connection failed: " + e.getMessage(),
                "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initComponents() {
        setTitle("Parking App - Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(204, 0, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel lblTitle = new JLabel("PARKING APP");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(204, 0, 0), 2),
            "Admin Login"
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Admin Name:"), gbc);
        
        gbc.gridx = 1;
        txtAdminName = new JTextField(20);
        loginPanel.add(txtAdminName, gbc);
  
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        loginPanel.add(txtPassword, gbc);
     
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(204, 0, 0));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(120, 40));
        btnLogin.addActionListener(e -> performLogin());
        
        txtPassword.addActionListener(e -> performLogin());
        
        loginPanel.add(btnLogin, gbc);
        
        //later i will create a test data for this
        JLabel lblInfo = new JLabel("Default: Herbert / password", JLabel.CENTER);
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        lblInfo.setForeground(Color.GRAY);
        
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        mainPanel.add(lblInfo, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    private void performLogin() {
        String adminName = txtAdminName.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        if (adminName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter both Admin Name and Password",
                "Login Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            AdminLogin admin = new AdminLogin(connection);
            if (admin.login(adminName, password)) {
                dispose();
                new AdminDashboardGUI(connection, adminName);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Invalid Admin Name or Password",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Login error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new AdminLoginGUI();
        });
    }
}