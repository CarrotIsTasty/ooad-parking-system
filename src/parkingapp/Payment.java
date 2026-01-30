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


public class Payment {
    public String paymentID;
    public double amountPaid;
    public PaymentMethod paymentMethod;
    public LocalDateTime paidAt;
    public Receipt receipt;
    
    public String createReceipt(){return "create the receipt implementation please";}
}
