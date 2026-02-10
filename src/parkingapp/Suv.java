package parkingapp;

public class Suv extends Vehicle{
    public Suv(String platenumber, boolean isVip){
        super(platenumber, isVip, VehicleType.SUV);
    }
    
     @Override
    public boolean canParkIn(Spot spot) {
    return spot.getSpotType().equals(SpotType.REGULAR);
    }
}
