package parkingapp;

enum SpotType {
    COMPACT, REGULAR, RESERVED
}

enum SpotStatus {
    AVAILABLE, OCCUPIED
}

public class Spot {
    private String spotId;
    private int spotNumber;
    private Row row;
    private SpotType spotType;
    private SpotStatus status;
    
    public Spot(){
       
    }
    
    public Spot(int id){
        
    }
    
    public Spot(Row row, int spotNumber, SpotType spotType, SpotStatus status) {
        this.row = row;
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        this.status = status;
        this.spotId = generateSpotId();
    }
    
    private String generateSpotId() {
        return String.format("F%d-R%d-S%d", row.getFloor().getFloorNumber(), row.getRowNumber(), spotNumber);
    }
    
    public String getSpotId() { //this too
        return spotId;
    }
   
    public int getSpotNumber() { //this need update
        return spotNumber;
    }
    
    public Row getRow() {
        return row;
    }

    public SpotType getSpotType() {
        return spotType;
    }
    
    public void setSpotType(SpotType spotType){
        this.spotType = spotType;
    }
    
    public SpotStatus getStatus() { //getTicket(spotID) if null return true
        return status;
    }
    
    public void setStatus(SpotStatus status) { //setTicket(spotID) if spotID is not null, call generateTicket(spotID)
        this.status = status;
    }
    
    public boolean canPark(Vehicle vehicle) {   
        if (status == SpotStatus.OCCUPIED) {
            return false;
        }
        
        VehicleType vType = vehicle.getType();
        
        switch (spotType) {
            case COMPACT:
                return vType == VehicleType.MOTORCYCLE || vType == VehicleType.BICYCLE || vType == VehicleType.CAR;
            case REGULAR:
                return vType == VehicleType.CAR || vType == VehicleType.SUV || vType == VehicleType.TRUCK;
            default:
                return false;
        }
    }
}


//checked