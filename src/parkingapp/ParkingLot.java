package parkingapp;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private String name;
    private List<Floor> floors;
    
    public ParkingLot(String name) {
        this.name = name;
        this.floors = new ArrayList<>();
    }
    
    public void addFloor(Floor floor) {
        floors.add(floor);
    }
    
    public String getName() {
        return name;
    }
    
    //getAllFloor method iteration with parameters
    public Floor getFloorByNumber(int floorNumber) {
        for (Floor floor : floors) {
            if (floor.getFloorNumber() == floorNumber) {
                return floor;
            }
        }
        return null;
       }
    
    public List<Floor> getFloors() {
        return floors;
    }
}
