package parkingapp;

public class Sedan extends Vehicle{
    
    public Sedan(String platenumber, boolean isVip){
        super(platenumber, isVip, VehicleType.SEDAN);
    }
    
}
