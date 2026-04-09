package dao;

import java.sql.*;
import java.util.*;
import model.Drink;
import model.Food;
import model.MenuSnackItem;

public class MenuSnackItemDAO extends BaseDAO implements DAO<MenuSnackItem> {

    // NOTE: BaseDAO already has a getConnection()

    // Creates a MenuSnackItem
    @Override
    public void create(MenuSnackItem item) {
        System.out.println("Creating new menu item...");

        try (
            PreparedStatement stmt = getConnection().prepareStatement(
                "INSERT INTO Menu(type, name, price, quantity) VALUES(?, ?, ?, ?)"
            );
        ) {

            stmt.setString(1, item.getType());    
            stmt.setString(2, item.getName());
            stmt.setFloat(3, item.getPrice());
            stmt.setInt(4, item.getAmountInStock());

            stmt.executeUpdate();

        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    // Retrieves a MenuSnackItem by ID
    @Override
    public MenuSnackItem getById(int id) {
        try (
            PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT * FROM Menu WHERE id = ?"
            );
        ) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (rs.getString("type").equals("Food")) {
                    return new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getInt("quantity")
                    );
                }

                return new Drink(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getFloat("price"),
                    rs.getInt("quantity")
                );
            }

        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return null; // Nothing found
    }

    // Searches for an item by name
    public MenuSnackItem getByName(String name) {
        try (
            PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT * FROM Menu WHERE name = ?"
            );
        ) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (rs.getString("type").equals("Food")) {
                    return new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getInt("quantity")
                    );
                }

                return new Drink(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getFloat("price"),
                    rs.getInt("quantity")
                );
            }

        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return null; // Nothing found
    }

    // Return all menu items
    @Override
    public List<MenuSnackItem> getAll() {
        List<MenuSnackItem> items = new ArrayList<>();

        try (
            Statement stmt = getConnection().createStatement();
        ) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM Menu");
            while (rs.next()) {
                MenuSnackItem it;
                if (rs.getString("type").equals("Food")) {
                    it = new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getInt("quantity")
                    );

                } else {
                    it = new Drink(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getInt("quantity")
                    );
                }

                items.add(it);
            }

        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return items; // Will be null if nothing found
    }

    // Updates quantity for a Menu Item (Hardcoded updatable fields)
    @Override
    public void update(MenuSnackItem item) {
        try (
            PreparedStatement stmt = getConnection().prepareStatement(
                "UPDATE Menu SET quantity = ? WHERE id = ?"
            );
        ) {

            stmt.setInt(1, item.getAmountInStock());
            stmt.setInt(2, item.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove a Menu Item by ID
    @Override
    public void delete(int id) {
        try (
            PreparedStatement stmt = getConnection().prepareStatement(
                "DELETE FROM Menu WHERE id = ?"
            );
        ) {
    
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }

}