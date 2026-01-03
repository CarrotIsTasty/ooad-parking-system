package parkingapp;

public class Motorcycle extends Vehicle{
    public Motorcycle(String platenumber, boolean isHandicapped, boolean isVip){
        super(platenumber, isHandicapped, isVip, VehicleType.MOTORCYCLE);
    }
}