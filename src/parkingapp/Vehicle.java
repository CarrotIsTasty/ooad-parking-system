package parkingapp;

public abstract class Vehicle {
    private String plateNumber;
    private boolean handicapped;
    private boolean vip;
    private VehicleType type;
    
    public Vehicle(String plateNumber, boolean handicapped, boolean vip, VehicleType type){
        this.plateNumber = plateNumber;
        this.handicapped = handicapped;    
        this.vip = vip;
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

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public boolean isVip() {
        return vip;
    }

     public boolean isHandicapped() {
        return handicapped;
    }
     
    public void setHandicapped(boolean handicapped) {
        this.handicapped = handicapped;
    }

}
