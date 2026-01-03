package parkingapp;

public class Truck extends Vehicle{
    public Truck(String platenumber, boolean handicapped, boolean vip){
        super(platenumber, handicapped, vip, VehicleType.TRUCK);
    }
}