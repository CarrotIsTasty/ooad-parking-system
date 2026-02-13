/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parkingapp;
import Database.DatabaseManager;
import java.math.BigDecimal;
/**
 *
 * @author herbertp
 */


public class Payment {
    public double amountPaid;
    public double parkingFee;
    public double fineAmount;
    public String ticketid;
    public String plate;
    public PaymentMethod paymentMethod;
    public Receipt receipt;
    public Fine fine;
    
    public Payment(double amountPaid, double parkingFee , double fineAmount, String ticketid, String plate,PaymentMethod method){
        this.amountPaid = amountPaid;
        this.parkingFee = parkingFee;
        this.fineAmount = fineAmount;
        this.ticketid = ticketid;
        this.plate = plate;
        this.paymentMethod = method;
    }
    
   public void setFine(){
       if (this.fineAmount != 0.0){
           DatabaseManager db = DatabaseManager.getInstance();
           db.saveFine(this.plate, this.fineAmount);
       }
       
   }
    
    
    public String getPlate(){
        return this.plate;
    }
    
    public double getAmountPaid(){
        return this.amountPaid;
    }
    
    public double getParkingFee(){
        return this.parkingFee;
    }
    
    public double getFineAmount(){
        return this.fineAmount;
    }
    
    
    public PaymentMethod getPaymentMethod(){
        return this.paymentMethod;
    }
    
    public String getTicketid(){
        return this.ticketid;
    }
    
    public boolean saveInDb(){
        DatabaseManager db = DatabaseManager.getInstance();
        boolean save = db.savePayment(this);
        return save;
    }
    
    
    public String createReceipt(){return "create the receipt implementation please";}
}
