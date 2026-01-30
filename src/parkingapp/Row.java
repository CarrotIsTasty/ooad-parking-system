package parkingapp;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private int rowNumber;
    private List<Spot> spots;
    private Floor floor;
    
    public Row(Floor floor, int rowNumber) {
        this.floor = floor;
        this.rowNumber = rowNumber;
        this.spots = new ArrayList<>();
    }
    
    public void addSpot(Spot spot) {
        spots.add(spot);
    }
    
    public Spot getSpot(int id){
        return spots.get(id);
    }
    
    public List<Spot> getAllSpots() {
    return spots;
    }
    
    public int getRowNumber() { 
        return rowNumber;
    }
    
    public Floor getFloor() {
        return floor;
    }
}


//checked