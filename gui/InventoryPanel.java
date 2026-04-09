package gui;

import dao.IngredientDAO;
import model.Ingredient;
import model.Manager;
import model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryPanel extends JPanel {
    private static final IngredientDAO dao = new IngredientDAO();
    private final Staff currentUser;

    private JTable table;
    private DefaultTableModel tableModel;

    public InventoryPanel(Staff currentUser) {
        this.currentUser = currentUser;
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Quantity"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton createBtn = new JButton("Create");
        JButton findBtn = new JButton("Find");
        JButton refreshBtn = new JButton("Refresh");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton reStockBtn = new JButton("Restock");

        buttonPanel.add(createBtn);
        buttonPanel.add(findBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(reStockBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        if (!currentUser.isAdmin()) {
            createBtn.setEnabled(false);
            updateBtn.setEnabled(false);
            deleteBtn.setEnabled(false);
            reStockBtn.setEnabled(false);
        }

        // Actions
        refreshBtn.addActionListener(e -> refreshTableWithAll());
        findBtn.addActionListener(e -> findIngredient());
        createBtn.addActionListener(e -> createIngredient());
        updateBtn.addActionListener(e -> updateIngredient());
        deleteBtn.addActionListener(e -> deleteIngredient());
        reStockBtn.addActionListener(e -> restockIngredient());

        refreshTableWithAll();
    }

    private void refreshTable(List<Ingredient> ingredients) {
        tableModel.setRowCount(0);
        for (Ingredient i : ingredients) {
            tableModel.addRow(new Object[]{i.getIngredientId(), i.getName(), i.getAmountInStock()});
        }
    }

    private void refreshTableWithAll() {
        refreshTable(dao.getAll());
    }

    private void findIngredient() {
        String name = JOptionPane.showInputDialog(this, "Enter name:");
        if (name == null || name.trim().isEmpty()) return;

        Ingredient ing = dao.getByName(name.trim());
        if (ing == null) {
            JOptionPane.showMessageDialog(this, "Not found");
            return;
        }

        List<Ingredient> list = new ArrayList<>();
        list.add(ing);
        refreshTable(list);
    }

    private void createIngredient() {
        JTextField nameField = new JTextField();
        JTextField qtyField = new JTextField();
        Object[] msg = {"Name:", nameField, "Quantity:", qtyField};

        if (JOptionPane.showConfirmDialog(this, msg, "Create Ingredient", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                int qty = Integer.parseInt(qtyField.getText().trim());
                if (name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
                dao.create(new Ingredient(0, name, qty));
                refreshTableWithAll();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
        }
    }

    private void updateIngredient() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a row first"); return; }

        int id = (int) tableModel.getValueAt(row, 0);
        String name = tableModel.getValueAt(row, 1).toString();
        int qty = (int) tableModel.getValueAt(row, 2);

        JTextField nameField = new JTextField(name);
        JTextField qtyField = new JTextField(String.valueOf(qty));
        Object[] msg = {"Name:", nameField, "Quantity:", qtyField};

        if (JOptionPane.showConfirmDialog(this, msg, "Update Ingredient", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                dao.update(new Ingredient(id, nameField.getText().trim(), Integer.parseInt(qtyField.getText().trim())));
                refreshTableWithAll();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error"); }
        }
    }

    private void deleteIngredient() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a row first"); return; }

        int id = (int) tableModel.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dao.delete(id);
            refreshTableWithAll();
        }
    }

    private void restockIngredient() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a row first"); return; }

        int id = (int) tableModel.getValueAt(row, 0);
        String input = JOptionPane.showInputDialog(this, "Enter amount:");
        if (input == null) return;

        try {
            int amount = Integer.parseInt(input);
            if (amount <= 0) throw new IllegalArgumentException();

            Ingredient ingredient = dao.getById(id);
            Manager manager = new Manager(currentUser.getId(), currentUser.getName(), currentUser.getPassword());
            manager.restock(ingredient, amount);
            dao.update(ingredient);
            refreshTableWithAll();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount");
        }
    }
}