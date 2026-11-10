
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Year;

public class BrowseDeals extends JPanel {

    private JEditorPane itineraryArea;

    private JComboBox<String> airportBox;
    private JComboBox<String> dayBox;
    private JComboBox<String> monthBox;
    private JComboBox<String> yearBox;
    private JComboBox<String> timeBox;

    public BrowseDeals() {

        //Create empty JPanel canvas
        setPreferredSize(new Dimension(500, 600));
        setLayout(null);

        setBackground(Color.WHITE);
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // center column width
        int panelWidth = getPreferredSize().width;
        int formWidth = 300;
        int startX = (panelWidth - formWidth) / 2;

        // Add title to the JPanel
        JLabel titleLabel = new JLabel("Browse Low-Demand Deals");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(startX, 12, formWidth, 28);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        // Add header for airport selector
        JLabel airportLabel = new JLabel("Airport:");
        airportLabel.setBounds(startX, 78, 100, 20);
        add(airportLabel);

        //Add available airports to the list
        String[] airports = {
                "Heathrow", "Gatwick", "Stansted", "Luton",
                "East Midlands", "Birmingham", "Manchester", "Edinburgh"
        };

        //Create a combobox with the available airports
        airportBox = new JComboBox<>(airports);
        airportBox.setBounds(startX, 100, formWidth, 24);
        add(airportBox);

        // Add a label for the date selector
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(startX, 140, 100, 20);
        add(dateLabel);

        // Add combo box for selecting days
        dayBox = new JComboBox<>();
        int dayWidth = 60;
        int monthWidth = 140;
        int yearWidth = 80;
        int gap = 10;
        int dayX = startX;
        int monthX = dayX + dayWidth + gap;
        int yearX = monthX + monthWidth + gap;

        dayBox.setBounds(dayX, 162, dayWidth, 25);
        add(dayBox);

        //Add months in the year
        String[] months = {
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };

        //Add combo box with the months inside
        monthBox = new JComboBox<>(months);
        monthBox.setBounds(monthX, 162, monthWidth, 25);
        add(monthBox);

        // Add the year combobox and set the initial value to the current year
        String[] years = new String[6];
        int currentYear = Year.now().getValue();

        for (int i = 0; i < years.length; i++) {
            years[i] = String.valueOf(currentYear + i);
        }

        yearBox = new JComboBox<>(years);
        yearBox.setBounds(yearX, 162, yearWidth, 25);
        add(yearBox);

        // Add button that filters dates to selected low demand dates from OffPeakDates.java
        JButton filterDates = new JButton("Filter Low-Demand Dates");
        int btnWidth = 220;
        int btnX = startX + (formWidth - btnWidth) / 2;
        filterDates.setBounds(btnX, 200, btnWidth, 30);
        add(filterDates);

        filterDates.addActionListener(e -> applyMidweekFilter());

        // Create a time box
        initialiseTimeBox();

        //Create the label for the time combobox
        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setBounds(startX, 240, 100, 20);
        add(timeLabel);

        timeBox.setBounds(startX, 260, 120, 25);
        add(timeBox);

        //Add a brief note so that customers understand 5% increase when selecting base price but during high demand times
        JLabel timingNote = new JLabel(
                "<html><i><span style='color:gray'>Note: 5% Surcharge for high-demand times between 6:00am and 9:00pm.</span></i></html>"
        );
        timingNote.setBounds(startX + 130, 262, formWidth - 130, 25);
        timingNote.setFont(new Font("Arial", Font.PLAIN, 10));
        add(timingNote);

        // Add button that filters times to low demand times
        JButton filterTimes = new JButton("Filter Low-Demand Times");
        filterTimes.setBounds(btnX, 300, btnWidth, 30);
        add(filterTimes);

        filterTimes.addActionListener(event-> applyOffPeakTimeFilter());

        // Add itinerary area that will display information after filtered selection
        itineraryArea = new JEditorPane();
        itineraryArea.setContentType("text/html");
        itineraryArea.setEditable(false);
        itineraryArea.setOpaque(false);
        itineraryArea.setBounds(startX, 340, formWidth, 145);
        itineraryArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(itineraryArea);

        // Add main functionality button that incorporates discounts and base prices from OffPeakDates and DealPricing
        JButton calculateButton = new JButton("Calculate Discount");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        int calcBtnWidth = 220;
        int calcBtnHeight = 45;
        int calcBtnX = startX + (formWidth - calcBtnWidth) / 2;
        int calcBtnY = getPreferredSize().height - calcBtnHeight - 15;
        calculateButton.setBounds(calcBtnX, calcBtnY, calcBtnWidth, calcBtnHeight);
        add(calculateButton);

        calculateButton.addActionListener(e -> calculatePrice());

        //Call updateDayBox method
        updateDayBox();

        monthBox.addActionListener(e -> updateDayBox());
        yearBox.addActionListener(e -> updateDayBox());

        // Back button that takes page back to Browse tariffs
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        int backBtnWidth = 90;
        int backBtnHeight = 30;
        int backBtnX = 15; // bottom-left margin
        int backBtnY = getPreferredSize().height - backBtnHeight - 15;
        backButton.setBounds(backBtnX, backBtnY, backBtnWidth, backBtnHeight);
        add(backButton);

        //Add back button that moves back to browsePanel
        backButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(BrowseDeals.this);
            if (frame != null) {
                frame.getContentPane().removeAll();
                frame.setContentPane(new browsePanel());
                frame.revalidate();
                frame.repaint();
            } else {
                JFrame f = new JFrame("Services");
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setContentPane(new browsePanel());
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }

    // Method that ensures that each month shows exactly how many days are in it
    private void updateDayBox() {

        int month = monthBox.getSelectedIndex() + 1;
        int year = Integer.parseInt((String) yearBox.getSelectedItem());

        int daysInMonth = OffPeakDates.getDaysInMonth(month, year);

        dayBox.removeAllItems();

        for (int i = 1; i <= daysInMonth; i++) {
            dayBox.addItem(String.valueOf(i));
        }

        if (dayBox.getItemCount() > 0) {
            dayBox.setSelectedIndex(0);
        }
    }

    // Method that filters months to show only days Tuesday, Wednesday and Thursday for low-demand dates
    private void applyMidweekFilter() {

        int month = monthBox.getSelectedIndex() + 1;
        int year = Integer.parseInt((String) yearBox.getSelectedItem());

        dayBox.removeAllItems();

        int daysInMonth = OffPeakDates.getDaysInMonth(month, year);

        for (int day = 1; day <= daysInMonth; day++) {

            LocalDate date = LocalDate.of(year, month, day);

            if (OffPeakDates.isMidweek(date)) {
                dayBox.addItem(String.valueOf(day));
            }
        }

        if (dayBox.getItemCount() > 0) {
            dayBox.setSelectedIndex(0);
        }
    }

    // Calculates price after selection and prints to itinerary box.
    public void calculatePrice() {

        String airport = (String) airportBox.getSelectedItem();

        int day = Integer.parseInt((String) dayBox.getSelectedItem());
        int month = monthBox.getSelectedIndex() + 1;
        int year = Integer.parseInt((String) yearBox.getSelectedItem());

        String time = (String) timeBox.getSelectedItem();

        LocalDate date = LocalDate.of(year, month, day);

        double basePrice = DealPricing.getBasePrice(airport);
        double discount = OffPeakDates.getDiscount(date);

        double finalPrice = basePrice * (1 - (discount - OffPeakDates.getTimeDiscount(time)) / 100.0);
        double saving = basePrice - finalPrice;

        String html = "<html><body style='font-family:monospace; font-size:12px; text-align:center; margin:8px;'>" +
                "<div>Airport: " + escapeHtml(airport) + "</div>" +
                "<div>Date: " + day + "/" + month + "/" + year + "</div>" +
                "<div>Time: " + escapeHtml(time) + "</div>" +
                "<div>Base Price: WAS £" + String.format("%.2f", basePrice) + "</div>" +
                "<div><b>Current Price: NOW £" + String.format("%.2f", finalPrice) + "</b></div>" +
                "<div><span style='color:red;'>Saving: £" + String.format("%.2f", saving) + "</span></div>" +
                "</body></html>";

        itineraryArea.setText(html);
        itineraryArea.setCaretPosition(0);
    }

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    // method that creates a combobox of times from 00:00 to 23:30 in 30 min increments
    private void initialiseTimeBox() {

        timeBox = new JComboBox<>();

        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min += 30) {
                timeBox.addItem(String.format("%02d:%02d", hour, min));
            }
        }
    }

    //Method that filters times to low-demand times
    private void applyOffPeakTimeFilter() {

        timeBox.removeAllItems();

        for (int hour = 0; hour < 24; hour++) {

            // Only include off-peak hours
            if (hour >= 21 || hour < 6) {

                for (int min = 0; min < 60; min += 30) {

                    String time = String.format("%02d:%02d", hour, min);
                    timeBox.addItem(time);
                }
            }
        }

        if (timeBox.getItemCount() > 0) {
            timeBox.setSelectedIndex(0);
        }
    }

    // getters for testing button functionality
    public JComboBox<String> getTimeBox() {
        return timeBox;
    }

    public JComboBox<String> getAirportBox() {
        return airportBox;
    }

    public JEditorPane getItineraryArea() {
        return itineraryArea;
    }
}





