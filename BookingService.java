// BookingService.java
import java.util.ArrayList;

public class BookingService {

    public static ArrayList<Booking> cart = new ArrayList<>();

    public static int calculateTripDuration(String location, int luggage) {
        if (location == null) location = "";
        int baseTime = 30;
        int traffic = (int)(Math.random() * 20);
        int luggageTime = luggage * 5;
        int locationTime = location.length();

        return baseTime + traffic + luggageTime + locationTime;
    }

    public static void addBooking(Booking booking) {
        cart.add(booking);
    }
}

