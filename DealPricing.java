
import java.util.HashMap;

public class DealPricing {

    private static final HashMap<String, Double> basePrices = new HashMap<>();

    static {
        basePrices.put("Heathrow", 65.0);
        basePrices.put("Gatwick", 55.0);
        basePrices.put("Stansted", 60.0);
        basePrices.put("Luton", 45.0);

        basePrices.put("East Midlands", 85.0);
        basePrices.put("Birmingham", 90.0);
        basePrices.put("Manchester", 130.0);
        basePrices.put("Edinburgh", 180.0);
    }

    public static double getBasePrice(String airport) {
        return basePrices.get(airport);
    }
}
