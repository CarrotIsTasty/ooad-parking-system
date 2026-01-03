package parkingapp;

public class Motorcycle extends Vehicle{
    public Motorcycle(String platenumber, boolean handicapped, boolean vip){
        super(platenumber, handicapped, vip, VehicleType.MOTORCYCLE);
    }
}