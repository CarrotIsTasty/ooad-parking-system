/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategy;

/**
 *
 * @author herbert
 */
public class HourlyFineStrategy implements FineCalculationStrategy {
    @Override
    public double calculateFine(int hoursOverstayed) {
        return hoursOverstayed * 20.0;
    }
    
    @Override
    public String getSchemeName() {
        return "HOURLY";
    }
    
}
