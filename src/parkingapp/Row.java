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
    
    public int getRowNumber() {
        return rowNumber;
    }
    
    public List<Spot> getSpots() {
        return spots;
    }
    
    public Floor getFloor() {
        return floor;
    }
}