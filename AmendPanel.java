import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.text.SimpleDateFormat;

public class AmendPanel {

    private JPanel panel;
    private JComboBox<String> bookingList;

    public AmendPanel() {

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        bookingList = new JComboBox<>();

        panel.add(new JLabel("Select Booking"));
        panel.add(bookingList);

        // Date picker
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);

        // Time picker
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);

        JButton amendButton = new JButton("Amend Booking");

        panel.add(new JLabel("New Date"));
        panel.add(dateSpinner);

        panel.add(new JLabel("New Time"));
        panel.add(timeSpinner);

        panel.add(amendButton);

        // Refresh list now (covers the common case) and whenever the panel becomes visible
        refreshBookingList();
        panel.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && panel.isShowing()) {
                refreshBookingList();
            }
        });

        amendButton.addActionListener(e -> {

            int selectedBooking = bookingList.getSelectedIndex();

            if (selectedBooking == -1) {
                JOptionPane.showMessageDialog(
                        panel,
                        "Please select a booking."
                );
                return;
            }

            String newDate = dateEditor.getFormat().format(dateSpinner.getValue());
            String newTime = timeEditor.getFormat().format(timeSpinner.getValue());

            Booking booking = BookingService.cart.get(selectedBooking);

            // Availability Check
            boolean available = true;

            for (int i = 0; i < BookingService.cart.size(); i++) {
                if (i != selectedBooking) {
                    Booking other = BookingService.cart.get(i);
                    if (other.date.equals(newDate) && other.time.equals(newTime)) {
                        available = false;
                    }
                }
            }

            if (!available) {
                JOptionPane.showMessageDialog(
                        panel,
                        "Booking amended successfully."
                );

                // close amend window
                SwingUtilities.getWindowAncestor(panel).dispose();

                // reopen cart with updated data
                JFrame cartFrame = new JFrame("Cart");
                cartFrame.setSize(600, 400);
                cartFrame.setContentPane(new CartUI().getPanel());
                cartFrame.setLocationRelativeTo(null);
                cartFrame.setVisible(true);

                return;
            }

            // Amend booking
            booking.date = newDate;
            booking.time = newTime;

            // Recalculate affected users
            for (Booking affected : BookingService.cart) {
                if (affected != booking && affected.airport.equals(booking.airport)) {
                    affected.duration += 5;
                }
            }

            JOptionPane.showMessageDialog(
                    panel,
                    "Booking amended successfully.\n" +
                            "Affected passengers have had 5 minutes added to their journey. Please refresh to view the amended time!"
            );

            // Keep UI in sync after changes
            refreshBookingList();
        });
    }

    private void refreshBookingList() {
        bookingList.removeAllItems();
        for (int i = 0; i < BookingService.cart.size(); i++) {
            Booking b = BookingService.cart.get(i);
            bookingList.addItem(
                    "Booking " + (i + 1)
                            + " - "
                            + b.airport
                            + " - "
                            + b.date
                            + " "
                            + b.time
            );
        }
        if (bookingList.getItemCount() == 0) {
            bookingList.addItem("No bookings available");
            bookingList.setSelectedIndex(-1);
        } else {
            bookingList.setSelectedIndex(0);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
