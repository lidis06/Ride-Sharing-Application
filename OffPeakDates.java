
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public class OffPeakDates {

    // Get discount based on discount logic
    public static double getDiscount(LocalDate date) {

        double discount = 0;

        // Every midweek day gets a 7.5% discount
        DayOfWeek day = date.getDayOfWeek();

        if (day == DayOfWeek.TUESDAY ||
                day == DayOfWeek.WEDNESDAY ||
                day == DayOfWeek.THURSDAY) {
            discount += 7.5;
        }

        // Additional 10% Discount in months January, February and November
        int month = date.getMonthValue();

        if (month == 1 || month == 2 || month == 11) {
            discount += 10;
        }

        return discount;
    }

    // To check that the date is a midweek date
    public static boolean isMidweek(LocalDate date) {

        DayOfWeek day = date.getDayOfWeek();

        return day == DayOfWeek.TUESDAY
                || day == DayOfWeek.WEDNESDAY
                || day == DayOfWeek.THURSDAY;
    }

    // Get the correct days in the month to avoid hard coded dates
    public static int getDaysInMonth(int month, int year) {
        return YearMonth.of(year, month).lengthOfMonth();
    }

    // Get time discount
    public static double getTimeDiscount(String time) {

        if (time == null) return 0;

        int hour = Integer.parseInt(time.substring(0, 2));

        // Nighttime: 21:00 - 06:00 → -5%
        if (hour >= 21 || hour < 6) {
            return -5;
        }

        // Daytime: 06:00 - 21:00 → +5%
        return 5;
    }
}