package parkingapp;

public class Motorcycle extends Vehicle{
    
    public Motorcycle(String platenumber, boolean isVip){
        super(platenumber, isVip, VehicleType.MOTORCYCLE);
    }
    
      @Override
    public boolean canParkIn(Spot spot) {
    return spot.getSpotType().equals(SpotType.COMPACT);
    }
}