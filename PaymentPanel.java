
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.prefs.Preferences;

public class PaymentPanel extends JPanel {

    private final Preferences prefs = Preferences.userNodeForPackage(PaymentPanel.class);

    public PaymentPanel() {

        // Load persisted values into PaymentStorage (if any)
        PaymentStorage.email = prefs.get("email", null);
        PaymentStorage.mobile = prefs.get("mobile", null);
        PaymentStorage.cardNumber = prefs.get("cardNumber", null);
        PaymentStorage.expiry = prefs.get("expiry", null);
        // Do NOT load cvc
        PaymentStorage.fullName = prefs.get("fullName", null);
        PaymentStorage.postcode = prefs.get("postcode", null);

        // Panel settings
        setPreferredSize(new Dimension(500, 600));
        setLayout(null);

        // Labels / totals (unchanged)
        JLabel titleLabel = new JLabel("Total:");
        titleLabel.setBounds(340, 450, 200, 30);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(titleLabel);

        int totalAmount = (int)(Math.random() * (90 - 10) + 10);
        JLabel totalAmountLabel = new JLabel("£"+ totalAmount+".00");
        totalAmountLabel.setBounds(340, 480, 200, 30);
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 36));
        add(totalAmountLabel);

        JLabel contactInfo = new JLabel("Contact information");
        contactInfo.setBounds(20, 15, 200, 30);
        contactInfo.setFont(new Font("Arial", Font.PLAIN, 18));
        add(contactInfo);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(30, 45, 100, 30);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(emailLabel);

        JTextField emailInput = new JTextField();
        emailInput.setBounds(30, 70, 200, 30);

        // If a saved email exists, show it in black
        if (PaymentStorage.email != null && !PaymentStorage.email.isEmpty()) {
            emailInput.setText(PaymentStorage.email);
            emailInput.setForeground(Color.BLACK);
        }

        add(emailInput);

        JLabel mobileLabel = new JLabel("Mobile");
        mobileLabel.setBounds(260, 45, 100, 30);
        mobileLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(mobileLabel);

        JTextField mobileInput = new JTextField();
        mobileInput.setBounds(260, 70, 200, 30);

        if (PaymentStorage.mobile != null && !PaymentStorage.mobile.isEmpty()) {
            mobileInput.setText(PaymentStorage.mobile);
            mobileInput.setForeground(Color.BLACK);
        }

        add(mobileInput);

        JLabel cardInfoLabel = new JLabel("Card information");
        cardInfoLabel.setBounds(20, 120, 200, 30);
        cardInfoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(cardInfoLabel);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(null);
        cardPanel.setBounds(30, 140, 600, 100);
        add(cardPanel);

        // Card number field
        JTextField cardField = new JTextField();
        cardField.setBounds(0, 20, 335, 30);
        cardPanel.add(cardField);

        // Expiry field
        JTextField expiryField = new JTextField();
        expiryField.setBounds(0, 55, 215, 30);
        cardPanel.add(expiryField);

        // CVC field
        JTextField cvcField = new JTextField();
        cvcField.setBounds(215, 55, 215, 30);
        cardPanel.add(cvcField);

        // Image icons

        // Visa icon
        ImageIcon visaIcon = new ImageIcon(getClass().getResource("/images/visa.png"));
        JLabel visaLabel = new JLabel(visaIcon);
        visaLabel.setBounds(345, 10, visaIcon.getIconWidth(), visaIcon.getIconHeight());
        cardPanel.add(visaLabel);

        // Mastercard icon
        ImageIcon mastercardIcon = new ImageIcon(getClass().getResource("/images/mastercard.png"));
        JLabel mastercardLabel = new JLabel(mastercardIcon);
        mastercardLabel.setBounds(388, 10, mastercardIcon.getIconWidth(), mastercardIcon.getIconHeight());
        cardPanel.add(mastercardLabel);

        // Cardholder name
        JLabel cardName = new JLabel("Cardholder name");
        cardName.setBounds(20, 235, 200, 30);
        cardName.setFont(new Font("Arial", Font.PLAIN, 18));
        add(cardName);

        JTextField cardNameField = new JTextField();
        cardNameField.setBounds(30, 280, 250, 30);
        add(cardNameField);

        JLabel postCodeLabel = new JLabel("Postcode");
        postCodeLabel.setBounds(20, 325, 200, 30);
        postCodeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(postCodeLabel);

        JTextField postCodeField = new JTextField();
        postCodeField.setBounds(30, 370, 250, 30);
        add(postCodeField);

        // Apply placeholder behaviour
        applyPlaceholder(cardField, "1234 1234 1234 1234", PaymentStorage.cardNumber);
        applyPlaceholder(expiryField, "MM/YY", PaymentStorage.expiry);

        // CVC must always load with placeholder "cvc"
        applyPlaceholder(cvcField, "cvc", null);

        applyPlaceholder(cardNameField, "Full name on card", PaymentStorage.fullName);
        applyPlaceholder(postCodeField, "E.g CT2 8BW", PaymentStorage.postcode);

        // Pay Now button
        JButton payButton = new JButton("Pay Now");
        payButton.setBounds(30, 450, 200, 60);
        payButton.setFont(new Font("Arial", Font.BOLD, 16));

        // java
        payButton.addActionListener(e -> {

            PaymentStorage.email = emailInput.getText();
            PaymentStorage.mobile = mobileInput.getText();
            PaymentStorage.cardNumber = cardField.getText();
            PaymentStorage.expiry = expiryField.getText();
            PaymentStorage.cvc = cvcField.getText();
            PaymentStorage.fullName = cardNameField.getText();
            PaymentStorage.postcode = postCodeField.getText();

            // Persist saved details (do not store cvc)
            prefs.put("email", PaymentStorage.email != null ? PaymentStorage.email : "");
            prefs.put("mobile", PaymentStorage.mobile != null ? PaymentStorage.mobile : "");
            prefs.put("cardNumber", PaymentStorage.cardNumber != null ? PaymentStorage.cardNumber : "");
            prefs.put("expiry", PaymentStorage.expiry != null ? PaymentStorage.expiry : "");
            prefs.put("fullName", PaymentStorage.fullName != null ? PaymentStorage.fullName : "");
            prefs.put("postcode", PaymentStorage.postcode != null ? PaymentStorage.postcode : "");

            try {
                prefs.flush();
            } catch (Exception ex) {
                // ignore
            }

            // Ensure fields that now contain real input are displayed in black
            if (!emailInput.getText().isEmpty()) emailInput.setForeground(Color.BLACK);
            if (!mobileInput.getText().isEmpty()) mobileInput.setForeground(Color.BLACK);
            if (!cardField.getText().isEmpty()) cardField.setForeground(Color.BLACK);
            if (!expiryField.getText().isEmpty()) expiryField.setForeground(Color.BLACK);
            if (!cardNameField.getText().isEmpty()) cardNameField.setForeground(Color.BLACK);
            if (!postCodeField.getText().isEmpty()) postCodeField.setForeground(Color.BLACK);

            JOptionPane.showMessageDialog(null, "Payment details saved.");

            // Open Confirmation UI: handle both possible Confirmation types (JFrame or JPanel)
            Object confirmation = new Confirmation("","","","","","",0);

            if (confirmation instanceof JFrame) {
                JFrame confFrame = (JFrame) confirmation;
                confFrame.setLocationRelativeTo(null);
                confFrame.setVisible(true);
            } else if (confirmation instanceof JPanel) {
                JFrame confFrame = new JFrame("Confirmation");
                confFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                confFrame.setSize(500, 650);
                confFrame.setContentPane((JPanel) confirmation);
                confFrame.setLocationRelativeTo(null);
                confFrame.setVisible(true);
            }

            // Close current payment window
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(PaymentPanel.this);
            if (current != null) current.dispose();

            revalidate();
        });
        add(payButton);

        //Back to homepage button
        JButton backToHomepage = new JButton("Back to homepage");
        backToHomepage.setBounds(30,550,150,30);
        backToHomepage.setFont(new Font("Arial",Font.BOLD,12));
        add(backToHomepage);

        backToHomepage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PaymentPanel.this);

                frame.dispose();

                mainWindow home = new mainWindow();
                home.setVisible(true);
            }
        });

        // Add new details
        JButton newDetailsButton = new JButton("Add New Details");
        newDetailsButton.setBounds(260, 15, 200, 25);
        newDetailsButton.setFont(new Font("Arial", Font.BOLD, 12));

        // Button functionality
        newDetailsButton.addActionListener(e -> {

            // Clear in-memory PaymentStorage
            PaymentStorage.email = "";
            PaymentStorage.mobile = "";
            PaymentStorage.cardNumber = "";
            PaymentStorage.expiry = "";
            PaymentStorage.cvc = "";
            PaymentStorage.fullName = "";
            PaymentStorage.postcode = "";

            // Remove persisted values
            prefs.remove("email");
            prefs.remove("mobile");
            prefs.remove("cardNumber");
            prefs.remove("expiry");
            prefs.remove("fullName");
            prefs.remove("postcode");

            try {
                prefs.flush();
            } catch (Exception ex) {

            }

            // Reset UI fields to initial placeholder state
            emailInput.setText("");
            emailInput.setForeground(Color.BLACK);

            mobileInput.setText("");
            mobileInput.setForeground(Color.BLACK);

            cardField.setText("1234 1234 1234 1234");
            cardField.setForeground(Color.GRAY);

            expiryField.setText("MM/YY");
            expiryField.setForeground(Color.GRAY);

            cvcField.setText("cvc");
            cvcField.setForeground(Color.GRAY);

            cardNameField.setText("Full name on card");
            cardNameField.setForeground(Color.GRAY);

            postCodeField.setText("E.g CT2 8BW");
            postCodeField.setForeground(Color.GRAY);
        });

        add(newDetailsButton);
    }

    /**
     * Sets up placeholder behaviour for a text field.
     * If savedValue is non-null and non-empty, that value is shown in black.
     * Otherwise the placeholder is shown in gray.
     */
    private void applyPlaceholder(JTextField field, String placeholder, String savedValue) {

        if (savedValue != null && !savedValue.isEmpty()) {

            field.setText(savedValue);
            field.setForeground(Color.BLACK);

        } else {

            field.setText(placeholder);
            field.setForeground(Color.GRAY);
        }

        field.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if (field.getText().equals(placeholder)) {
                    field.setText("");
                }

                field.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (field.getText().trim().isEmpty()) {

                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);

                } else {

                    field.setForeground(Color.BLACK);
                }
            }
        });
    }
}


