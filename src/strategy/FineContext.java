/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategy;

import Config.FileConfigurationService;

public class FineContext {
    private FineCalculationStrategy strategy;
    
    public FineContext(FineCalculationStrategy strategy) {
        this.strategy = strategy;
    }
    
    public FineContext() {
        FileConfigurationService config = FileConfigurationService.getInstance("./config.ini");
        String strategyName = config.getString("fineStrategy", "FIXED");
        
        switch (strategyName) {
            case "FIXED":
                this.strategy = new FixedFineStrategy();
                break;
            
            case "HOURLY":
                this.strategy = new HourlyFineStrategy();
                break;
            
            case "PROGRESSIVE":
                this.strategy = new ProgressiveFineStrategy();
                break;
            
            default:
                this.strategy = new FixedFineStrategy();
        }
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