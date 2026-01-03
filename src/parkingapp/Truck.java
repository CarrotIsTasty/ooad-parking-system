package parkingapp;

public class Truck extends Vehicle{
    public Truck(String platenumber, boolean isHandicapped, boolean isVip){
        super(platenumber, isHandicapped, isVip, VehicleType.TRUCK);
    }
}