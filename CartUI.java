// java
import javax.swing.*;

public class CartUI {

    private JPanel panel;

    public CartUI() {

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (BookingService.cart.isEmpty()) {
            panel.add(new JLabel("No bookings in cart."));
        } else {

            for (int i = 0; i < BookingService.cart.size(); i++) {

                var b = BookingService.cart.get(i);

                panel.add(new JLabel("Booking " + (i + 1)));
                panel.add(new JLabel("Airport: " + b.airport));
                panel.add(new JLabel("Luggage: " + b.luggage));
                panel.add(new JLabel("Date: " + b.date));
                panel.add(new JLabel("Time: " + b.time));
                panel.add(new JLabel("Pickup: " + b.pickup));
                panel.add(new JLabel("Duration: " + b.duration + " mins"));
                panel.add(new JLabel("--------------"));
            }
        }

        JButton amendButton = new JButton("Amend Booking");

        panel.add(amendButton);

        amendButton.addActionListener(e -> {

            JFrame amendFrame = new JFrame("Amend Booking");

            amendFrame.setSize(500, 400);

            amendFrame.setContentPane(
                    new AmendBookingUI().getPanel()
            );

            amendFrame.setLocationRelativeTo(null);

            amendFrame.setVisible(true);
        });

        //  Proceed to payment button
        JButton paymentButton = new JButton("Proceed to Payment");

        panel.add(paymentButton);

        paymentButton.addActionListener(e -> {
            JFrame paymentFrame = new JFrame("Payment");
            paymentFrame.setSize(500, 650);

            // PaymentPanel is a JPanel; set it directly as content pane
            paymentFrame.setContentPane(new PaymentPanel());

            paymentFrame.setLocationRelativeTo(null); // centres window
            paymentFrame.setVisible(true);

            // close cart window if present
            var parentWindow = SwingUtilities.getWindowAncestor(panel);
            if (parentWindow != null) parentWindow.dispose();
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
