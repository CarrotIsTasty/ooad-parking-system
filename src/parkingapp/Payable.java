/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parkingapp;

import java.time.LocalDateTime;

/**
 *
 * @author herbertp
 */
public abstract class Payable {
    public Vehicle vehicle;
    public LocalDateTime createdAt;
    public double totalFee;
    public Payment payment;
    
    public Payment getpayment(){
        return payment;
    }
    
    public void setPayment(Payment payment){
        this.payment = payment;
    }
    
    
    public abstract double calculateFees();
}
