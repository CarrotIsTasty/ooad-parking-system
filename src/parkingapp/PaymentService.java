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
    
   /* public double calculateTotalDue(){
        return 
    }*/
}
