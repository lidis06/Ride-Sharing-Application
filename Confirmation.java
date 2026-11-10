
import javax.swing.*;
import java.awt.*;

public class Confirmation extends JPanel {

    Confirmation(String vehicleType, String luggage, String pickup, String dropoff, String date, String time, int totalPrice) {

        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label1 = new JLabel("Booking Details:");
        label1.setFont(new Font("SansSerif", Font.BOLD, 20));
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label1);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel label2 = new JLabel("Vehicle Type: Standard " + vehicleType);
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label2);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel labelLuggage = new JLabel("Luggage: 1 Large Suitcase " + luggage);
        labelLuggage.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(labelLuggage);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel label3 = new JLabel("Pickup Location: Kent " + pickup);
        label3.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label3);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel labelDropoff = new JLabel("Dropoff Location: Gatwick " + dropoff);
        labelDropoff.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(labelDropoff);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel label4 = new JLabel("Date: 01/06/2026 " + date);
        label4.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label4);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel label5 = new JLabel("Time: 07:00AM " + time);
        label5.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label5);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel label6 = new JLabel("Total Price: £4" + totalPrice);
        label6.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label6);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        JButton button = new JButton("Book Ride");
        button.setForeground(Color.BLACK);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Booking Confirmed");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            mainWindow main = new mainWindow();
            frame.setContentPane(main.mainPanel);
            frame.revalidate();
            frame.repaint();
        });
        panel.add(button);

        this.add(panel, BorderLayout.CENTER);
    }
}