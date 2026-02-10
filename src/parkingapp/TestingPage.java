package parkingapp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestingPage {
    public static void main(String[] args) {
        ReserveHourTest();
    }
    
    public static void ReserveHourTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime ceiledTime = ceilToNext30Minutes(timeNow);

        String reserve30 = ceiledTime.plusMinutes(30).format(formatter);
        String reserve60 = ceiledTime.plusMinutes(60).format(formatter);
        
        System.out.println("Reserve 1: " + ceiledTime.format(formatter));
        System.out.println("Reserve 2: " + reserve30);
        System.out.println("Reserve 3: " + reserve60);
    }
    
    public static LocalDateTime ceilToNext30Minutes(LocalDateTime time) {
        int minute = time.getMinute();
        int remainder = minute % 30;

        if (remainder == 0) {
            return time.withSecond(0).withNano(0);
        }

        return time
                .plusMinutes(30 - remainder)
                .withSecond(0)
                .withNano(0);
    }
    
    public static void ParkingFeeTest() {
        String entry = "2026-02-10 14:20"; 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime entryTime = LocalDateTime.parse(entry, formatter);
        
        LocalDateTime exitTime = LocalDateTime.now(); //2026-02-10 17:00
        
        String stringEntry = entryTime.format(formatter);
        String stringExit = exitTime.format(formatter);
        System.out.println(stringEntry);
        System.out.println(stringExit);
        
        double hourlyRate = 5.0;

        Duration duration = Duration.between(entryTime, exitTime);
        long totalMinutes = duration.toMinutes();

        double billableHours = Math.ceil(totalMinutes / 60.0);

        double totalFee = billableHours * hourlyRate;

        System.out.println("Total Minutes Parked: " + totalMinutes);
        System.out.println("Billable Hours: " + billableHours);
        System.out.println("Total Fee: RM " + totalFee);
    }
}
