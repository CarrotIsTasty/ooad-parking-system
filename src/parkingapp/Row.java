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
    
    //getSpot method iteration with parameters
    
    
    public int getRowNumber() { 
        return rowNumber;
    }
    
        public Spot getSpotByNumber(int spotNumber) {
        for (Spot spot : spots) {
            if (spot.getSpotNumber() == spotNumber) {
                return spot;
            }
        }
        return null;
       }
    
    public List<Spot> getSpots() { //getSpot method iteration
        return spots;
    }
    
    public Floor getFloor() {
        return floor;
    }
}