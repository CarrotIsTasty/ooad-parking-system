package parkingapp;

public class Sedan extends Vehicle{
    
    public Sedan(String platenumber, boolean handicapped, boolean vip){
        super(platenumber, handicapped, vip, VehicleType.SEDAN);
    }
    
}
