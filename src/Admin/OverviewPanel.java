package Admin;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OverviewPanel extends JPanel {
    private JLabel occupancyLabel;
    private JLabel revenueLabel;
    private JLabel totalSpotsLabel;
    private JLabel occupiedSpotsLabel;
    private JTable floorsTable;
    private JTable spotsTable;
    private JLabel spotCountLabel;
    private Connection connection;
    
    public OverviewPanel(Connection connection) {
        this.connection = connection;
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // TOP: Statistics Panel
        JPanel statsPanel = createStatisticsPanel();
        add(statsPanel, BorderLayout.NORTH);
        
        // CENTER: Parking Structure in split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(createFloorsPanel());
        splitPane.setRightComponent(createSpotsPanel());
        splitPane.setDividerLocation(300);
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    private JPanel createStatisticsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        statsPanel.setBorder(new TitledBorder("Key Statistics"));
        
        // Occupancy Rate
        Object[] occupancyCard = createStatCard("Occupancy Rate", "0%", Color.BLUE);
        statsPanel.add((JPanel) occupancyCard[0]);
        occupancyLabel = (JLabel) occupancyCard[1];
        
        // Revenue
        Object[] revenueCard = createStatCard("Total Revenue", "RM 0.00", new Color(0, 150, 0));
        statsPanel.add((JPanel) revenueCard[0]);
        revenueLabel = (JLabel) revenueCard[1];
        
        // Total Spots
        Object[] totalSpotsCard = createStatCard("Total Spots", "0", Color.BLACK);
        statsPanel.add((JPanel) totalSpotsCard[0]);
        totalSpotsLabel = (JLabel) totalSpotsCard[1];
        
        // Occupied Spots
        Object[] occupiedCard = createStatCard("Occupied Spots", "0", Color.RED);
        statsPanel.add((JPanel) occupiedCard[0]);
        occupiedSpotsLabel = (JLabel) occupiedCard[1];
        
        return statsPanel;
    }
    
    private Object[] createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createEtchedBorder());
        card.add(new JLabel(title, SwingConstants.CENTER), BorderLayout.NORTH);
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(color);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return new Object[]{card, valueLabel};
    }
    
    private JPanel createFloorsPanel() {
        JPanel floorsPanel = new JPanel(new BorderLayout());
        floorsPanel.setBorder(new TitledBorder("All Floors"));
        
        // Table to show floors
        String[] columns = {"Floor #", "Total Spots", "Available", "Occupied", "Occupancy %"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        floorsTable = new JTable(model);
        floorsTable.setRowHeight(30);
        
        // Color rows based on occupancy
        floorsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (column == 4 && value != null) {
                    try {
                        String occupancyStr = value.toString().replace("%", "");
                        double occupancy = Double.parseDouble(occupancyStr);
                        
                        if (occupancy >= 90) {
                            c.setBackground(new Color(255, 200, 200));
                        } else if (occupancy >= 70) {
                            c.setBackground(new Color(255, 255, 200));
                        } else {
                            c.setBackground(Color.WHITE);
                        }
                    } catch (NumberFormatException e) {
                        c.setBackground(Color.WHITE);
                    }
                } else {
                    c.setBackground(Color.WHITE);
                }
                
                return c;
            }
        });
        
        floorsPanel.add(new JScrollPane(floorsTable), BorderLayout.CENTER);
        
        // Refresh button
        JButton refreshButton = new JButton("Refresh Floors Data");
        refreshButton.addActionListener(e -> loadFloorsData());
        floorsPanel.add(refreshButton, BorderLayout.SOUTH);
        
        return floorsPanel;
    }
    
    private JPanel createSpotsPanel() {
        JPanel spotsPanel = new JPanel(new BorderLayout());
        spotsPanel.setBorder(new TitledBorder("All Parking Spots"));
        
        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filter by Floor:"));
        
        JComboBox<String> floorFilter = new JComboBox<>(new String[]{"All", "1", "2", "3", "4", "5"});
        filterPanel.add(floorFilter);
        
        filterPanel.add(new JLabel("Filter by Type:"));
        JComboBox<String> typeFilter = new JComboBox<>(new String[]{"All", "Compact", "Regular", "Handicapped", "Reserved"});
        filterPanel.add(typeFilter);
        
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(e -> filterSpots(
            floorFilter.getSelectedItem().toString(),
            typeFilter.getSelectedItem().toString()
        ));
        filterPanel.add(filterButton);
        
        spotsPanel.add(filterPanel, BorderLayout.NORTH);
        
        // Spots table
        String[] columns = {"Spot ID", "Floor", "Type", "Hourly Rate", "Status", "Vehicle", "Entry Time"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        spotsTable = new JTable(model);
        spotsTable.setRowHeight(25);
        
        // Color coding for spot status
        spotsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                String status = table.getValueAt(row, 4).toString();
                
                if ("Available".equals(status)) {
                    c.setBackground(new Color(220, 255, 220));
                } else if ("Occupied".equals(status)) {
                    c.setBackground(new Color(255, 220, 220));
                } else if ("Reserved".equals(status)) {
                    c.setBackground(new Color(220, 220, 255));
                } else {
                    c.setBackground(Color.WHITE);
                }
                
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                }
                
                return c;
            }
        });
        
        spotsPanel.add(new JScrollPane(spotsTable), BorderLayout.CENTER);
        
        // Spot summary
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        summaryPanel.add(new JLabel("Showing: "));
        spotCountLabel = new JLabel("0 spots");
        summaryPanel.add(spotCountLabel);
        
        JButton refreshButton = new JButton("Refresh Spots");
        refreshButton.addActionListener(e -> loadSpotsData());
        summaryPanel.add(refreshButton);
        
        spotsPanel.add(summaryPanel, BorderLayout.SOUTH);
        
        return spotsPanel;
    }
    
    // ==================== DATA LOADING METHODS ====================
    
    public void loadData() {
        loadStatistics();
        loadFloorsData();
        loadSpotsData();
    }
    
    private void loadStatistics() {
        String query = """
            SELECT 
                COUNT(*) as total_spots,
                SUM(CASE WHEN is_available = 0 THEN 1 ELSE 0 END) as occupied_spots,
                ROUND((SUM(CASE WHEN is_available = 0 THEN 1 ELSE 0 END) * 100.0 / COUNT(*)), 1) as occupancy_rate,
                (SELECT COALESCE(SUM(amount), 0) FROM payments) as total_revenue
            FROM parking_spots
            """;
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                totalSpotsLabel.setText(String.valueOf(rs.getInt("total_spots")));
                occupiedSpotsLabel.setText(String.valueOf(rs.getInt("occupied_spots")));
                occupancyLabel.setText(rs.getDouble("occupancy_rate") + "%");
                revenueLabel.setText("RM " + String.format("%.2f", rs.getDouble("total_revenue")));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading statistics: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadFloorsData() {
        String query = """
            SELECT 
                floor_number,
                COUNT(*) as total_spots,
                SUM(CASE WHEN is_available = 1 THEN 1 ELSE 0 END) as available,
                SUM(CASE WHEN is_available = 0 THEN 1 ELSE 0 END) as occupied,
                ROUND((SUM(CASE WHEN is_available = 0 THEN 1 ELSE 0 END) * 100.0 / COUNT(*)), 1) as occupancy_rate
            FROM parking_spots
            GROUP BY floor_number
            ORDER BY floor_number
            """;
        
        DefaultTableModel model = (DefaultTableModel) floorsTable.getModel();
        model.setRowCount(0);
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("floor_number"),
                    rs.getInt("total_spots"),
                    rs.getInt("available"),
                    rs.getInt("occupied"),
                    rs.getDouble("occupancy_rate") + "%"
                };
                model.addRow(row);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void loadSpotsData() {
        loadSpotsData("All", "All");
    }
    
    private void loadSpotsData(String floorFilter, String typeFilter) {
        StringBuilder query = new StringBuilder("""
            SELECT 
                spot_id,
                floor_number,
                spot_type,
                hourly_rate,
                CASE 
                    WHEN is_available = 1 THEN 'Available'
                    ELSE 'Occupied'
                END as status,
                COALESCE(current_vehicle_plate, '-') as vehicle_plate,
                entry_time
            FROM parking_spots
            WHERE 1=1
            """);
        
        // Add filters
        if (!"All".equals(floorFilter)) {
            query.append(" AND floor_number = ").append(floorFilter);
        }
        if (!"All".equals(typeFilter)) {
            query.append(" AND spot_type = '").append(typeFilter.toUpperCase()).append("'");
        }
        
        query.append(" ORDER BY floor_number, spot_id");
        
        DefaultTableModel model = (DefaultTableModel) spotsTable.getModel();
        model.setRowCount(0);
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query.toString())) {
            
            int count = 0;
            while (rs.next()) {
                Object[] row = {
                    rs.getString("spot_id"),
                    rs.getInt("floor_number"),
                    rs.getString("spot_type"),
                    String.format("RM %.1f", rs.getDouble("hourly_rate")),
                    rs.getString("status"),
                    rs.getString("vehicle_plate"),
                    rs.getTimestamp("entry_time") != null ? 
                        rs.getTimestamp("entry_time").toString().substring(11, 16) : "-"
                };
                model.addRow(row);
                count++;
            }
            
            spotCountLabel.setText(count + " spots");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void filterSpots(String floorFilter, String typeFilter) {
        loadSpotsData(floorFilter, typeFilter);
    }
}