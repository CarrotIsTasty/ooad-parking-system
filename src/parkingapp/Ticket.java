
package parkingapp;

import java.time.LocalDateTime;

public class Ticket extends Payable {
    private int ticketID;
    private Vehicle vehicle;
    private Spot spot;
    private LocalDateTime createdAt;
    private LocalDateTime exitTime;
    private double totalFee;
    private Payment payment;
    
    public int getDuration(){return (exitTime.getHour()-createdAt.getHour());}
    public Payment getPayment(){return payment;}
    public void setPayment(Payment payment){
        this.payment = payment;
    }

    public int getTicketID() {
        return ticketID;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Spot getSpot() {
        return spot;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public double getTotalFee() {
        return totalFee;
    }
    
    
    
}
