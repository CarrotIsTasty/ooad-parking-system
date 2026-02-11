package parkingapp;

public class Truck extends Vehicle{
    public Truck(String platenumber, boolean isVip){
        super(platenumber, isVip, VehicleType.TRUCK);
    }
    
    
    public boolean canParkIn(Spot spot) {
    return spot.getSpotType().equals(SpotType.REGULAR);
    }
}