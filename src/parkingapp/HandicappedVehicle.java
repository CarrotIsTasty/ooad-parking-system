package parkingapp;

public class HandicappedVehicle extends Vehicle{
    public HandicappedVehicle(String platenumber, boolean isVip){
        super(platenumber, isVip, VehicleType.HANDICAPPED);
    }
}
