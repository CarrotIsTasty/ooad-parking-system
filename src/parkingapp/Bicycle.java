package parkingapp;

public class Bicycle extends Vehicle{
    public Bicycle(String platenumber, boolean isVip){
        super(platenumber, isVip, VehicleType.BICYCLE);
    }
    
    @Override
    public boolean canParkIn(Spot spot) {
    return spot.getSpotType().equals(SpotType.COMPACT);
    }
}
