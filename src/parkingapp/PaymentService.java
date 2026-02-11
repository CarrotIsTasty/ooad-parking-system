/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parkingapp;

import java.util.List;

/**
 *
 * @author herbertp
 */
public class PaymentService {
    public Payable payable;
    public Vehicle vehicle;
    public List<Fine> fines;
    public List<Payment> payments;
    public PaymentMethod paymentMethod;
    
    
    public void PaymentService(Payable payable){
        
    }
    
    public boolean processPayment(PaymentMethod paymentMethod){
        return true;
    }
    
    public Vehicle getVehicle(){
        return this.vehicle;
    }
    
    public List<Fine> getUnpaidFines(){
        return fines;
    }
    
//       Compact: For small vehicles (motorcycles, bicycles) - RM 2/hour
//       Regular: For regular cars - RM 5/hour
//       Handicapped: Reserved for handicapped vehicles - RM 2/hour (FREE only if
//       handicapped card holder vehicle parks in handicapped spot)
//       Reserved: For VIP customers - RM 10/hour
    
   /* public double calculateTotalDue(){
        return 
    }*/
}
