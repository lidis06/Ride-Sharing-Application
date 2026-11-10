import javax.swing.*;
import java.awt.event.HierarchyEvent;
import java.util.Date;

public class AmendBookingUI {

    private JPanel panel;
    private JComboBox<String> bookingBox;
    private JButton amendButton;

    public AmendBookingUI() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        bookingBox = new JComboBox<>();
        refreshBookingList(); // initial population

        // refresh whenever the panel becomes visible
        panel.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && panel.isShowing()) {
                refreshBookingList();
            }
        });

        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);

        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);

        amendButton = new JButton("Save Amendment");
        amendButton.addActionListener(e -> saveAmendment(dateEditor, dateSpinner, timeEditor, timeSpinner));

        panel.add(new JLabel("Select Booking"));
        panel.add(bookingBox);

        panel.add(new JLabel("New Date"));
        panel.add(dateSpinner);

        panel.add(new JLabel("New Time"));
        panel.add(timeSpinner);

        panel.add(amendButton);
    }

    private void refreshBookingList() {
        bookingBox.removeAllItems();
        if (BookingService.cart == null || BookingService.cart.isEmpty()) {
            bookingBox.addItem("No bookings available");
            bookingBox.setSelectedIndex(-1);
            amendButton.setEnabled(false);
            return;
        }

        for (int i = 0; i < BookingService.cart.size(); i++) {
            Booking b = BookingService.cart.get(i);
            bookingBox.addItem(
                    "Booking " + (i + 1)
                            + " - "
                            + b.airport
                            + " - "
                            + b.date
                            + " "
                            + b.time
            );
        }
        bookingBox.setSelectedIndex(0);
        amendButton.setEnabled(true);
    }

    private void saveAmendment(JSpinner.DateEditor dateEditor, JSpinner dateSpinner,
                               JSpinner.DateEditor timeEditor, JSpinner timeSpinner) {

        if (BookingService.cart == null || BookingService.cart.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "No booking selected.");
            return;
        }

        int index = bookingBox.getSelectedIndex();
        if (index < 0 || index >= BookingService.cart.size()) {
            JOptionPane.showMessageDialog(panel, "No booking selected.");
            return;
        }

        String newDate = dateEditor.getFormat().format(dateSpinner.getValue());
        String newTime = timeEditor.getFormat().format(timeSpinner.getValue());

        // Availability check
        boolean available = true;
        for (int i = 0; i < BookingService.cart.size(); i++) {
            if (i == index) continue;
            Booking other = BookingService.cart.get(i);
            if (other.date.equals(newDate) && other.time.equals(newTime)) {
                available = false;
                break;
            }
        }

        if (!available) {
            JOptionPane.showMessageDialog(panel, "Booking cannot be amended. Time slot already taken.");
            return;
        }

        // Update booking
        Booking booking = BookingService.cart.get(index);
        booking.date = newDate;
        booking.time = newTime;

        // Recalculate affected users
        for (int i = 0; i < BookingService.cart.size(); i++) {
            if (i == index) continue;
            Booking affected = BookingService.cart.get(i);
            if (affected.airport.equals(booking.airport)) {
                affected.duration += 5;
            }
        }

        JOptionPane.showMessageDialog(panel,
                "Booking amended successfully.\n" +
                        "Affected passengers have had 5 minutes added to their journey time.");

        // keep UI in sync
        refreshBookingList();
    }

    public JPanel getPanel() {
        return panel;
    }
}
