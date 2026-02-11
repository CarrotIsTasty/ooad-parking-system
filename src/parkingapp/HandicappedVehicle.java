package parkingapp;


public class HandicappedVehicle extends Vehicle{
    
    private boolean hasValidCard;
    
    
    public HandicappedVehicle(String platenumber, boolean isVip){
        super(platenumber, isVip, VehicleType.HANDICAPPED);
    }
    
    public boolean hasValidCard() {
        return hasValidCard;
    }
    
    public double getDiscountedRate() {
        return hasValidCard ? 2.0 : 0;
    }
    
    
    public boolean canParkIn(Spot spot) {
    return true ;
    }
}
