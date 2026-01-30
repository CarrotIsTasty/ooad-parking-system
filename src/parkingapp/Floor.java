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
    
    public void addRow(Row row) {rows.add(row);}
    
    //Must do for loop to iterate through list
    public List<Row> getAllRow(){return this.rows;}
    
    public Row getRow(int id){return this.rows.get(id);}

    public int getFloorNumber() {return floorNumber;}
   
    public ParkingLot getParkingLot() {return parkingLot;}
}

//checked