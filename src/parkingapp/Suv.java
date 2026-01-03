package parkingapp;

public class Suv extends Vehicle{
    public Suv(String platenumber, boolean isHandicapped, boolean isVip){
        super(platenumber, isHandicapped, isVip, VehicleType.SUV);
    }
}
