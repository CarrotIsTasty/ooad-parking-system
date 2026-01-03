package parkingapp;

import java.util.ArrayList;
import java.util.List;

public class Floor {
    private int floorNumber;
    private List<Row> rows;
    private ParkingLot parkingLot;
    
    public Floor(ParkingLot parkingLot, int floorNumber) {
        this.parkingLot = parkingLot;
        this.floorNumber = floorNumber;
        this.rows = new ArrayList<>();
    }
    
    public void addRow(Row row) {
        rows.add(row);
    }
    //getAllFloor method
    public int getFloorNumber() {
        return floorNumber;
    }
    
    public List<Row> getRows() {
        return rows;
    }
    
    public ParkingLot getParkingLot() {
        return parkingLot;
    }
}