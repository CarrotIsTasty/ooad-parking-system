/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategy;

/**
 *
 * @author herbert
 */
public class ProgressiveFineStrategy implements FineCalculationStrategy {
    @Override
    public double calculateFine(int hoursOverstayed) {
        double fine = 0.0;
        
        if (hoursOverstayed > 72) {
            fine = 50 + 100 + 150 + 200; // RM500 total
        } else if (hoursOverstayed > 48) {
            fine = 50 + 100 + 150; // RM300
        } else if (hoursOverstayed > 24) {
            fine = 50 + 100; // RM150
        } else if (hoursOverstayed > 0) {
            fine = 50; // RM50
        }
        
        return fine;
    }
    
    @Override
    public String getSchemeName() {
        return "PROGRESSIVE";
    }
    
}
