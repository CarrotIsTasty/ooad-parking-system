/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategy;

public class FineContext {
    private FineCalculationStrategy strategy;
    
    public FineContext(FineCalculationStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(FineCalculationStrategy strategy) {
        this.strategy = strategy;
    }
    
    public double calculateFine(int hoursOverstayed) {
        return strategy.calculateFine(hoursOverstayed);
    }
    
    public String getSchemeName() {
        return strategy.getSchemeName();
    }
}