package parkingapp;
import java.util.Scanner;

public class ParkingApp {

    public static void main(String[] args) {
        System.out.println("+   University Parking System   +");
        System.out.println("|       1. Start Parking        |");
        System.out.println("|       2. Reserve Parking      |");
        System.out.println("+       3. Exit Parking         +");
        System.out.println("Please Select (1-3): ");
        Scanner opt = new Scanner(System.in);
        int option = opt.nextInt();
    }
    
}
