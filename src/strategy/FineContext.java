/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategy;
import parkingapp.*;
import Config.FileConfigurationService;
import java.time.LocalDateTime;
import java.io.IOException;

public class FineContext {
    private FineCalculationStrategy strategy;
    private String currentFineScheme;
    private FileConfigurationService config;
    
    public FineContext(FineCalculationStrategy strategy) {
        this.strategy = strategy;
        this.config = FileConfigurationService.getInstance("./config.ini");
        this.currentFineScheme = strategy.getSchemeName();
    }
    
    public FineContext() {
        this.config = FileConfigurationService.getInstance("./config.ini");
        
        // Load configuration from file
        try {
            config.load();
        } catch (IOException e) {
            System.err.println("Could not load configuration: " + e.getMessage());
        }
        
        String strategyName = config.getString("fineStrategy", "FIXED");
        this.currentFineScheme = strategyName;
        
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
                this.currentFineScheme = "FIXED";
        }
    }
    
    public double checkAndCalculateFines(Ticket ticket) {
        double totalFines = 0.0;
        
        LocalDateTime exitTime = ticket.getExitTime() != null ? 
            ticket.getExitTime() : LocalDateTime.now();

        long hoursParked = ticket.getDuration();       

        // Check if over 24 hours
        if (hoursParked > 24) {
            int hoursOverstayed = (int) (hoursParked - 24);
            double overstayFine = this.calculateFine(hoursOverstayed);
            totalFines += overstayFine;
        }

        return totalFines;
    }
    
    public void setStrategyByName(String fineName) {
        this.currentFineScheme = fineName;
        
        switch (fineName) {
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
                this.currentFineScheme = "FIXED";
        }
        
        // Persist the change to config file
        persistStrategy();
    }

    public void setStrategy(FineCalculationStrategy strategy) {
        this.strategy = strategy;
        this.currentFineScheme = strategy.getSchemeName();
        
        // Persist the change to config file
        persistStrategy();
    }
    
    /**
     * Save the current strategy to the configuration file
     */
    private void persistStrategy() {
        config.set("fineStrategy", this.currentFineScheme);
        
        try {
            config.save();
            System.out.println("Fine strategy '" + this.currentFineScheme + "' saved to configuration.");
        } catch (IOException e) {
            System.err.println("Failed to save fine strategy to configuration: " + e.getMessage());
        }
    }
    
    /**
     * Reload the strategy from the configuration file
     */
    public void reloadFromConfig() {
        try {
            config.load();
            String strategyName = config.getString("fineStrategy", "FIXED");
            
            if (!strategyName.equals(this.currentFineScheme)) {
                System.out.println("Reloading fine strategy from config: " + strategyName);
                setStrategyByName(strategyName);
            }
        } catch (IOException e) {
            System.err.println("Failed to reload configuration: " + e.getMessage());
        }
    }
    
    public double calculateFine(int hoursOverstayed) {
        return strategy.calculateFine(hoursOverstayed);
    }
    
    public String getSchemeName() {
        return strategy.getSchemeName();
    }
    
    public String getCurrentFineScheme() {
        return currentFineScheme;
    }
}