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
public class Fine extends Payable {
    public int fineID;
    public Vehicle vehicle;
    public LocalDateTime createdAt;
    public String reason;
    public double totalFee;
    public Payment payment;
    
    public Payment getPayment(){
        return this.payment;
    }
    
    @Override
    public void setPayment(Payment payment){
        this.payment = payment;
    }
    
    @Override
    public double calculateFees(){
        double fee = 2.0;
        return fee;
    };
}
