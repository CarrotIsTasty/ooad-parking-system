package parkingapp;

public class Car extends Vehicle{
    
    public Car(String platenumber, boolean isVip){
        super(platenumber, isVip, VehicleType.CAR);
    }
    
    public boolean canParkIn(Spot spot) {
    return spot.getSpotType().equals(SpotType.COMPACT) ||
           spot.getSpotType().equals(SpotType.REGULAR) ;
    
    }
}