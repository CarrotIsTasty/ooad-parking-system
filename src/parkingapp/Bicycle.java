package parkingapp;

public class Bicycle extends Vehicle{
    public Bicycle(String platenumber, boolean handicapped, boolean vip){
        super(platenumber, handicapped, vip, VehicleType.BICYCLE);
    }
}
