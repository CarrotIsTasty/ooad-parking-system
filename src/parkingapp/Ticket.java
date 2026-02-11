
package parkingapp;

import java.time.LocalDateTime;
import Database.DatabaseManager;
import java.time.format.DateTimeFormatter;

public class Ticket extends Payable {
    private String ticketID;
    private Vehicle vehicle;
    private Spot spot;
    private String entryTime;
    private LocalDateTime entryTimeFormat;
    private LocalDateTime createdAt;
    private LocalDateTime exitTime;
    private double totalFee;
    private Payment payment;
    private double hourlyRate;
    private int duration;
    private final DateTimeFormatter timeInHours = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    public Ticket(){
        
    }
    
    public Ticket(String ticketId, LocalDateTime entryTimeFormat, LocalDateTime exitTime, String spotId,String plateNumber){
        this.ticketID = ticketId;
        this.entryTimeFormat = entryTimeFormat;
        this.exitTime = exitTime;
        this.spot = new Spot(spotId);
        this.vehicle = new Vehicle(plateNumber);
    }
    
    public Ticket(String ticketId, String plateNumber, VehicleType type , String spotId, String entryTime ){
        this.ticketID = ticketId;
        this.vehicle = new Vehicle(plateNumber, type);
        this.spot = new Spot(spotId);
        this.entryTime = entryTime;
    }
    
    public int getDuration(){return duration;}
    
    public int getDurationFromNow(LocalDateTime now){
        this.duration = now.getHour() - entryTimeFormat.getHour();
        return duration;
    }
    
    public Payment getPayment(){return payment;}
    
    public void setPayment(Payment payment){
        this.payment = payment;
    }

    public String getTicketID() {
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

    public String getEntryTime(){
        return entryTime;
    }
    
    public String getEntryTimeFormat(){
        return entryTimeFormat.format(timeInHours);
    }
    public LocalDateTime getExitTime() {
        return exitTime;
    }
    
    public void setTotalFee(double total){
        this.totalFee = total;
    }

    public double getTotalFee() {
        return totalFee;
    }
    
    //       Compact: For small vehicles (motorcycles, bicycles) - RM 2/hour
//       Regular: For regular cars - RM 5/hour
//       Handicapped: Reserved for handicapped vehicles - RM 2/hour (FREE only if
//       handicapped card holder vehicle parks in handicapped spot)
//       Reserved: For VIP customers - RM 10/hour
    
    @Override
    public double calculateFees(){
       DatabaseManager db = DatabaseManager.getInstance();
       String spotType = db.getSpotTypeByLPlate(this.vehicle.getPlateNumber());
       
    switch(spotType){
        case "COMPACT": 
            this.hourlyRate = 2.0; 
            break;
        case "REGULAR": 
            this.hourlyRate = 5.0; 
            break;
        case "Handicapped": 
            this.hourlyRate = 2.0; 
            break;
        case "RESERVED": 
            this.hourlyRate = 10.0; 
            break;
        default:
            this.hourlyRate = 5.0;
    }
    
    
    return duration*hourlyRate;
           
       }
    }
    
