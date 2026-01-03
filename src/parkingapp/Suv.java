package parkingapp;

public class Suv extends Vehicle{
    public Suv(String platenumber, boolean handicapped, boolean vip){
        super(platenumber, handicapped, vip, VehicleType.SUV);
    }
}
