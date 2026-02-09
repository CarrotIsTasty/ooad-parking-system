/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;
import javax.swing.SwingUtilities;
import Database.DatabaseConnection;

/**
 *
 * @author herbert
 */
public class LaunchAdmin {
    public static void main(String[] args){
        DatabaseConnection.getConnection();
       
       
        SwingUtilities.invokeLater(() -> {
            new AdminLoginGUI();
        //    new AdminDashboardGUI();
    });
    
}
}
