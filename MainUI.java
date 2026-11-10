import javax.swing.*;

public class MainUI {

    private JPanel panel;

    public MainUI() {

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] airports = {"Heathrow", "Gatwick", "Stansted"};
        JComboBox<String> airportBox = new JComboBox<>(airports);

        JSpinner luggage = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));

        //  DATE PICKER
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);

        //  TIME PICKER
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);

        JTextField pickup = new JTextField();

        JButton addButton = new JButton("Add Booking");
        JButton viewCart = new JButton("View Cart");

        panel.add(new JLabel("Airport"));
        panel.add(airportBox);

        panel.add(new JLabel("Luggage"));
        panel.add(luggage);

        panel.add(new JLabel("Date"));
        panel.add(dateSpinner);

        panel.add(new JLabel("Time"));
        panel.add(timeSpinner);

        panel.add(new JLabel("Pickup"));
        panel.add(pickup);

        panel.add(addButton);
        panel.add(viewCart);

        addButton.addActionListener(e -> {

            String airport = (String) airportBox.getSelectedItem();
            int lug = (int) luggage.getValue();

            // convert spinner values to strings
            String date = dateEditor.getFormat().format(dateSpinner.getValue());
            String time = timeEditor.getFormat().format(timeSpinner.getValue());

            String p = pickup.getText();

            int duration = BookingService.calculateTripDuration(p, lug);

            Booking booking = new Booking(
                    airport, lug, date, time, p, duration
            );

            BookingService.addBooking(booking);

            JOptionPane.showMessageDialog(panel, "Booking Added!");
        });

        viewCart.addActionListener(e -> {
            JFrame cartFrame = new JFrame("Cart");
            cartFrame.setSize(600, 400);
            cartFrame.setContentPane(new CartUI().getPanel());
            cartFrame.setVisible(true);
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}