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
    
    //getAllFloor method iteration with parameter
    public Floor getFloor(int id){
        return floors.get(id);
       }
    
    public List<Floor> getFloors() {
        return floors;
    }
}

//checked
