package parkingapp;

public class Bicycle extends Vehicle{
    public Bicycle(String platenumber, boolean isHandicapped, boolean isVip){
        super(platenumber, isHandicapped, isVip, VehicleType.BICYCLE);
    }
}
