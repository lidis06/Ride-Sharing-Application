
public class Booking {
    public String airport;
    public int luggage;
    public String date;
    public String time;
    public String pickup;
    public int duration;

    // original 5-arg constructor
    public Booking(String airport, int luggage, String date, String time, String pickup) {
        this.airport = airport;
        this.luggage = luggage;
        this.date = date;
        this.time = time;
        this.pickup = pickup;
        this.duration = 0;
    }

    // overloaded 6-arg constructor to accept duration (matches MainUI usage)
    public Booking(String airport, int luggage, String date, String time, String pickup, int duration) {
        this.airport = airport;
        this.luggage = luggage;
        this.date = date;
        this.time = time;
        this.pickup = pickup;
        this.duration = duration;
    }
}

