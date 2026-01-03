package parkingapp;

public class Sedan extends Vehicle{
    
    public Sedan(String platenumber, boolean isHandicapped, boolean isVip){
        super(platenumber, isHandicapped, isVip, VehicleType.SEDAN);
    }
    
}
