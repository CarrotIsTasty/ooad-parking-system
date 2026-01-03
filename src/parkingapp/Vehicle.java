package parkingapp;

public abstract class Vehicle {
    private String plateNumber;
    private boolean isHandicapped;
    private boolean isVip;
    private VehicleType type;
    
    public Vehicle(String plateNumber, boolean isHandicapped, boolean isVip, VehicleType type){
        this.plateNumber = plateNumber;
        this.isHandicapped = isHandicapped;    
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
        return isHandicapped;
    }
     
    public void setHandicapped(boolean isHandicapped) {
        this.isHandicapped = isHandicapped;
    }

}
