package gui;

import java.awt.*;
import javax.swing.*;
import model.Staff;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Staff currentUser;

    public MainFrame(Staff currentUser) {
        this.currentUser = currentUser;

        setTitle("Main Window - " + currentUser.getName());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Panels
        InventoryPanel inventoryPanel = new InventoryPanel(currentUser);
        StaffPanel staffPanel = new StaffPanel(currentUser);

        mainPanel.add(inventoryPanel, "Inventory");
        mainPanel.add(staffPanel, "Staff");

        add(mainPanel, BorderLayout.CENTER);

        // -------------------------
        // Sidebar styling
        // -------------------------
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(30, 40, 60));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton inventoryBtn = createSidebarButton("Inventory");
        inventoryBtn.addActionListener(e -> cardLayout.show(mainPanel, "Inventory"));

        sidebar.add(inventoryBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // spacing

        if (currentUser.isAdmin() == 1) {
            JButton staffBtn = createSidebarButton("Staff");
            staffBtn.addActionListener(e -> cardLayout.show(mainPanel, "Staff"));
            sidebar.add(staffBtn);
        }

        add(sidebar, BorderLayout.WEST);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(200, 50));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 70, 100));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 90, 130));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 70, 100));
            }
        });

        return button;
    }
}