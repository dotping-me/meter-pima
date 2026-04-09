package gui;

import dao.StaffDAO;
import model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StaffPanel extends JPanel {
    private final StaffDAO dao = new StaffDAO();
    private final Staff currentUser;

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton createBtn, updateBtn, deleteBtn, refreshBtn;

    public StaffPanel(Staff currentUser) {
        this.currentUser = currentUser;
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Role"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        createBtn = new JButton("Create");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        refreshBtn = new JButton("Refresh");

        buttonPanel.add(createBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        if (!currentUser.isAdmin()) {
            createBtn.setEnabled(false);
            updateBtn.setEnabled(false);
            deleteBtn.setEnabled(false);
        }

        // Listeners
        createBtn.addActionListener(e -> createStaff());
        updateBtn.addActionListener(e -> updateStaff());
        deleteBtn.addActionListener(e -> deleteStaff());
        refreshBtn.addActionListener(e -> refreshTable());

        refreshTable();
    }

    private void refreshTable() {
        List<Staff> staffList = dao.getAll();
        tableModel.setRowCount(0);
        for (Staff s : staffList) {
            tableModel.addRow(new Object[]{s.getId(), s.getName(), s.isAdmin() ? "Manager" : "Cashier"});
        }
    }

    private void createStaff() {
        JTextField nameField = new JTextField();
        JTextField passwordField = new JTextField();
        JCheckBox adminCheck = new JCheckBox("Is Manager?");
        Object[] msg = {"Name:", nameField, "Password:", passwordField, "Role:", adminCheck};

        if (JOptionPane.showConfirmDialog(this, msg, "Create Staff", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String password = passwordField.getText().trim();
                boolean isAdmin = adminCheck.isSelected();
                if (name.isEmpty() || password.isEmpty())
                    throw new IllegalArgumentException("Name and password cannot be empty");

                dao.create(new Staff(0, name, password, isAdmin));
                refreshTable();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
        }
    }

    private void updateStaff() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a staff first"); return; }

        int id = (int) tableModel.getValueAt(row, 0);
        Staff staff = dao.getById(id);

        JTextField nameField = new JTextField(staff.getName());
        JTextField passwordField = new JTextField(staff.getPassword());
        JCheckBox adminCheck = new JCheckBox("Is Manager?", staff.isAdmin());
        Object[] msg = {"Name:", nameField, "Password:", passwordField, "Role:", adminCheck};

        if (JOptionPane.showConfirmDialog(this, msg, "Update Staff", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String password = passwordField.getText().trim();
                boolean isAdmin = adminCheck.isSelected();
                if (name.isEmpty() || password.isEmpty())
                    throw new IllegalArgumentException("Name and password cannot be empty");

                dao.update(new Staff(id, name, password, isAdmin));
                refreshTable();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
        }
    }

    private void deleteStaff() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a staff first"); return; }

        int id = (int) tableModel.getValueAt(row, 0);
        String name = tableModel.getValueAt(row, 1).toString();
        if (JOptionPane.showConfirmDialog(this, "Delete '" + name + "'?", "Confirm Delete", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dao.delete(id);
            refreshTable();
        }
    }
}