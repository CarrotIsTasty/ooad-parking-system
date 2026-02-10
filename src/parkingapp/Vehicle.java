package parkingapp;

import java.time.LocalDateTime;

public abstract class Vehicle {
    private String plateNumber;
    private VehicleType type;
    private boolean isVip;
    protected LocalDateTime entryTime;
    protected LocalDateTime exitTime;
    
    
    public Vehicle(String plateNumber, boolean isVip, VehicleType type){
        this.plateNumber = plateNumber;
        this.isVip = isVip;
        this.type = type;
    }
    

    public void setType(VehicleType type) {
        this.type = type;
    }

    public VehicleType getType() {
        return type;
    }

    public String getPlateNumber() {
        return plateNumber;
    }
    
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setVip(boolean isVip) {
        this.isVip = isVip;
    }

    public boolean isVip() {
        return isVip;
    }
    
    public boolean isHandicapped() {
        return this.type.equals(VehicleType.HANDICAPPED);
    }
    
    public abstract boolean canParkIn(Spot spot);
}
//checked