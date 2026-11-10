
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class mainWindow extends JFrame {

    public static void main(String[] args) {

        mainWindow app = new mainWindow();
        app.setVisible(true);
    }

    JPanel mainPanel;

    public mainWindow() {

        setTitle("Ride Sharing App");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with border around entire page
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new LineBorder(Color.BLACK, 3, true));

        // Title at top centre
        JLabel titleLabel = new JLabel("Homepage", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(20, 0, 10, 0));

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Button to open Browse Services panel - josh
        JButton browseButton = new JButton("Browse available services and tariffs");
        styleButton(browseButton);

        browseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setContentPane(new browsePanel());
                revalidate();
            }
        });

        // Button to open Booking panel - luka
        JButton bookingButton = new JButton("Make new booking");
        styleButton(bookingButton);

        bookingButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(new MainUI().getPanel());
                revalidate();
                //JOptionPane.showMessageDialog(null, "luka booking panel");
            }
        });

        // Button to open payment panel - hayden
        JButton paymentButton = new JButton("Pay for new booking");
        styleButton(paymentButton);

        paymentButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(new PaymentPanel());
                revalidate();
                //JOptionPane.showMessageDialog(null, "hayden payment panel");
            }
        });

        // Button to open confirmation panel - fostina
        JButton confirmationButton = new JButton("Confirmation");
        styleButton(confirmationButton);

        confirmationButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(new Confirmation("","","","","","",0));
                revalidate();
                //JOptionPane.showMessageDialog(null, "fostina confirm panel");
            }
        });

        // Buttons added to the panel
        buttonPanel.add(browseButton);
        buttonPanel.add(bookingButton);
        buttonPanel.add(paymentButton);
        buttonPanel.add(confirmationButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    // Applies standard styling and hover effects to all buttons
    private void styleButton(JButton button) {

        button.setFocusPainted(false);
        button.setBorder(new roundCorners(15));
        button.setBackground(Color.WHITE);
        button.setOpaque(true);

        // Mouse hover effect over buttons
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                button.setBackground(new Color(200, 200, 255));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {

                button.setBackground(Color.WHITE);
            }
        });
    }
}

// Implements the Swing Border interface
// Used to replace default square button borders with rounded corners
class roundCorners implements Border {

    private int radius;

    roundCorners(int radius) {

        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {

        return new Insets(10, 10, 10, 10);
    }

    @Override
    public boolean isBorderOpaque() {

        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}