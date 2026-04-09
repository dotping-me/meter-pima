package gui;

import dao.StaffDAO;
import javax.swing.*;
import model.Staff;

public class AuthFrame extends JFrame {

    private final StaffDAO dao = new StaffDAO();

    public AuthFrame() {
        setTitle("Staff Login");
        setSize(350, 200);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new java.awt.GridLayout(4, 2, 10, 10));
        setLocationRelativeTo(null);

        // Initialising elements
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton exitBtn = new JButton("Exit");

        // Adding elements to screen
        add(nameLabel); add(nameField);
        add(passwordLabel); add(passwordField);
        add(new JLabel()); add(new JLabel());
        add(loginBtn); add(exitBtn);

        // Attaching event listeners
        loginBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both name and password");
                return;
            }

            Staff staff = dao.getByName(name);
            if (staff == null || !staff.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
                return;
            }

            JOptionPane.showMessageDialog(this,
                "Welcome, " + staff.getName() + (staff.isAdmin() == 1 ? " (Admin)" : " (Staff)")
            );

            // Open single main window
            new MainFrame(staff).setVisible(true);
            dispose();
        });

        exitBtn.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new AuthFrame().setVisible(true));
    }
}