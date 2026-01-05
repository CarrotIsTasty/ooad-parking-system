package parkingapp;

public class Truck extends Vehicle{
    public Truck(String platenumber, boolean isVip){
        super(platenumber, isVip, VehicleType.TRUCK);
    }
}