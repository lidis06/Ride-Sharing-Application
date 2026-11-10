
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;

public class browsePanel extends JPanel {

    // Pricing constants
    public static final int PRICE_STANDARD       = 20;
    public static final int PRICE_LARGE          = 30;
    public static final int PRICE_SHARED         = 15;
    public static final int PRICE_EXTRA_LUGGAGE  = 5;
    public static final int PRICE_KENT_HEATHROW  = 13;
    public static final int PRICE_KENT_GATWICK   = 10;
    public static final int PRICE_DOVER_HEATHROW = 16;
    public static final int PRICE_DOVER_GATWICK  = 14;

    private JRadioButton standardButton;
    private JRadioButton largeButton;
    private JRadioButton sharedButton;

    private JComboBox<String> luggageBox;
    private JComboBox<String> pickupBox;
    private JComboBox<String> dropoffBox;
    private JComboBox<String> dayBox;
    private JComboBox<String> monthBox;
    private JComboBox<String> yearBox;
    private JComboBox<String> hourBox;
    private JComboBox<String> minuteBox;

    private JLabel totalLabel;
    private JLabel messageLabel;

    private int totalPrice = 0;

    public browsePanel() {

        setLayout(new BorderLayout(10, 10));

        // Top area: Browse Deals button (left) and title centered
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JButton browseDealsButton = new JButton("Browse Deals");
        browseDealsButton.setFocusable(false);
        // OPEN BrowseDeals panel inside the current window (or fallback to new window)
        browseDealsButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(browsePanel.this);
            if (frame != null) {
                frame.getContentPane().removeAll();
                frame.setContentPane(new BrowseDeals());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.revalidate();
                frame.repaint();
            } else {
                // Fallback: open a separate window if parent frame isn't found
                JFrame f = new JFrame("Browse Deals");
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setContentPane(new BrowseDeals());
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });

        JLabel titleLabel = new JLabel("Services", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

        topPanel.add(browseDealsButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        // Vehicle Type - Radio Buttons
        formPanel.add(new JLabel("Vehicle Type:"));

        JPanel vehiclePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        standardButton = new JRadioButton("Standard");
        largeButton = new JRadioButton("Large");
        sharedButton = new JRadioButton("Shared");

        ButtonGroup vehicleGroup = new ButtonGroup();
        vehicleGroup.add(standardButton);
        vehicleGroup.add(largeButton);
        vehicleGroup.add(sharedButton);

        standardButton.setSelected(true);

        vehiclePanel.add(standardButton);
        vehiclePanel.add(largeButton);
        vehiclePanel.add(sharedButton);

        formPanel.add(vehiclePanel);

        // Luggage options - dropdown
        formPanel.add(new JLabel("Luggage Items:"));

        luggageBox = new JComboBox<>(new String[]{
                "1 Large Suitcase",
                "2 Large Suitcases"
        });

        formPanel.add(luggageBox);

        // Pickup location - dropdown
        formPanel.add(new JLabel("Pickup Location:"));

        pickupBox = new JComboBox<>(new String[]{
                "Kent",
                "Dover",
                "Gatwick",
                "Heathrow"
        });

        formPanel.add(pickupBox);

        // Dropoff location - dropdown
        formPanel.add(new JLabel("Dropoff Location:"));

        dropoffBox = new JComboBox<>(new String[]{
                "Gatwick",
                "Heathrow",
                "Kent",
                "Dover"
        });

        formPanel.add(dropoffBox);

        // Date of travel - dropdown
        formPanel.add(new JLabel("Date:"));

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        dayBox = new JComboBox<>(new String[]{
                "1","2","3","4","5","6","7","8","9","10",
                "11","12","13","14","15","16","17","18","19","20",
                "21","22","23","24","25","26","27","28","29","30","31"
        });

        monthBox = new JComboBox<>(new String[]{
                "1","2","3","4","5","6","7","8","9","10","11","12"
        });

        yearBox = new JComboBox<>(new String[]{
                "2026","2027","2028"
        });

        datePanel.add(dayBox);
        datePanel.add(monthBox);
        datePanel.add(yearBox);

        formPanel.add(datePanel);

        // Time of travel - dropdown
        formPanel.add(new JLabel("Time:"));

        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        hourBox = new JComboBox<>(new String[]{
                "00","01","02","03","04","05","06","07","08","09",
                "10","11","12","13","14","15","16","17","18","19",
                "20","21","22","23"
        });

        minuteBox = new JComboBox<>(new String[]{
                "00","05","10","15","20","25","30","35","40","45","50","55"
        });

        timePanel.add(hourBox);
        timePanel.add(new JLabel(":"));
        timePanel.add(minuteBox);

        formPanel.add(timePanel);

        // Cost section to display estimated price after valid selections
        formPanel.add(new JLabel("Cost:"));

        JPanel costPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        totalLabel = new JLabel("£0");

        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 11));

        costPanel.add(totalLabel);
        costPanel.add(messageLabel);

        formPanel.add(costPanel);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        wrapper.add(formPanel, BorderLayout.CENTER);

        add(wrapper, BorderLayout.CENTER);

        // Buttons at the bottom of the panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton calculateButton = new JButton("Estimate Cost");
        JButton homeButton = new JButton("Homepage");
        JButton continueButton = new JButton("Continue to Booking");

        buttonPanel.add(calculateButton);
        buttonPanel.add(homeButton);
        buttonPanel.add(continueButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Estimate Cost Button
        // Error message displayed if invalid date and time selected
        // Error message displayed if an invalid route with no airport is selected
        // Cost will not be displayed in either case
        calculateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String pickup  = (String) pickupBox.getSelectedItem();
                String dropoff = (String) dropoffBox.getSelectedItem();

                if (!isValidDateTime()) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please select a valid date and time.",
                            "Invalid Date/Time",
                            JOptionPane.ERROR_MESSAGE
                    );

                } else if (isInvalidRoute(pickup, dropoff)) {

                    JOptionPane.showMessageDialog(
                            null,
                            "No airport selected.",
                            "Invalid Route",
                            JOptionPane.ERROR_MESSAGE
                    );

                } else {

                    String vehicleType = standardButton.isSelected() ? "Standard"
                            : largeButton.isSelected() ? "Large" : "Shared";

                    String luggage = (String) luggageBox.getSelectedItem();

                    totalPrice = calculatePrice(vehicleType, luggage, pickup, dropoff);

                    totalLabel.setText("£" + totalPrice);

                    messageLabel.setText(
                            "This is an estimated cost, please continue to make a booking"
                    );
                }
            }
        });

        // Homepage button
        homeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(browsePanel.this);

                frame.dispose();

                mainWindow home = new mainWindow();
                home.setVisible(true);
            }
        });

        // Continue to booking button
        continueButton.addActionListener(e -> {

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(browsePanel.this);

            if (frame != null) {
                frame.getContentPane().removeAll();
                frame.setContentPane(new MainUI().getPanel());
                frame.revalidate();
                frame.repaint();
            } else {
                System.out.println("Frame is null");
            }
        });
    }

    // Validation of date and time
    // Cannot continue to booking if date and time is not in the future
    private boolean isValidDateTime() {

        int day   = Integer.parseInt((String) dayBox.getSelectedItem());
        int month = Integer.parseInt((String) monthBox.getSelectedItem());
        int year  = Integer.parseInt((String) yearBox.getSelectedItem());
        int hour  = Integer.parseInt((String) hourBox.getSelectedItem());
        int minute = Integer.parseInt((String) minuteBox.getSelectedItem());

        LocalDateTime selectedDateTime = LocalDateTime.of(year, month, day, hour, minute);
        LocalDateTime currentDateTime  = LocalDateTime.now();

        return !selectedDateTime.isBefore(currentDateTime);
    }


    // Validation of pickup and dropoff route
    // Routes between two non-airport locations are invalid as no airport is involved
    public boolean isInvalidRoute(String pickup, String dropoff) {

        boolean pickupNonAirport  = pickup.equals("Kent")  || pickup.equals("Dover");
        boolean dropoffNonAirport = dropoff.equals("Kent") || dropoff.equals("Dover");

        return pickupNonAirport && dropoffNonAirport;
    }

    // Method to calculate the estimated price based on selections
    public static int calculatePrice(String vehicleType, String luggage, String pickup, String dropoff) {

        int total = 0;

        if (vehicleType.equals("Standard")) {
            total += PRICE_STANDARD;
        } else if (vehicleType.equals("Large")) {
            total += PRICE_LARGE;
        } else if (vehicleType.equals("Shared")) {
            total += PRICE_SHARED;
        }

        if (luggage.equals("2 Large Suitcases")) {
            total += PRICE_EXTRA_LUGGAGE;
        }

        if (pickup.equals("Kent") && dropoff.equals("Heathrow")) {
            total += PRICE_KENT_HEATHROW;
        } else if (pickup.equals("Kent") && dropoff.equals("Gatwick")) {
            total += PRICE_KENT_GATWICK;
        } else if (pickup.equals("Dover") && dropoff.equals("Heathrow")) {
            total += PRICE_DOVER_HEATHROW;
        } else if (pickup.equals("Dover") && dropoff.equals("Gatwick")) {
            total += PRICE_DOVER_GATWICK;
        }

        return total;
    }

    // Debugging purposes
    public static void main(String[] args) {

        JFrame frame = new JFrame("Ride Sharing App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        frame.setContentPane(new browsePanel());

        frame.setVisible(true);
    }
}
