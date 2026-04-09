// Database queries for Staff CRUD on signup/login
package dao;

import java.sql.*;
import java.util.*;
import model.Staff;

public class StaffDAO extends BaseDAO implements DAO<Staff> {

    // Create a staff
    @Override
    public void create(Staff staff) {
        try (
            // Prepare INSERT DML statement
            PreparedStatement stmt = getConnection().prepareStatement(
                "INSERT INTO Staff(name, password, is_admin) VALUES(?, ?, ?)"
            );
        ) {
            // Set name, password and admin privileges
            stmt.setString(1, staff.getName());
            stmt.setString(2, staff.getPassword());
            stmt.setInt(3, staff.isAdmin() ? 1 : 0); // admin set to 1 else 0
            stmt.executeUpdate();

        // Handle error(s)
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Return a specific staff by ID
    @Override
    public Staff getById(int id) {
        try (
            // Prepare SELECT DML statement
            PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT * FROM staff WHERE id = ?")) {
            // Get staff id
            stmt.setInt(1, id);
            // Execute the statement
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) { // Access the first row
                // Get the staff details
                return new Staff(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getInt("is_admin") == 1
                );
            }
        // Handle error(s)
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Return staff by name
    public Staff getByName(String name) {
        try (
            PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT * FROM Staff WHERE name = ?"
            );
        ) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Staff(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getInt("is_admin") == 1
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Not found
    }
    
    // Return all staffs
    @Override
    public List<Staff> getAll() {
        List<Staff> staffs = new ArrayList<>(); // Composition

        try (
            // Create a static SELECT DML statement
            Statement stmt = getConnection().createStatement()) {
            // Execute the statement
            ResultSet rs = stmt.executeQuery("SELECT * FROM staff");

            while (rs.next()) { // Access the first row
                staffs.add(new Staff(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getInt("is_admin") == 1
                ));
            }
        // Handle error(s)
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return the staff list
        return staffs;
    }

    // Update name/password for a specific staff
    @Override
    public void update(Staff staff) {
        try (
            // Prepare UPDATE DML statement
            PreparedStatement stmt = getConnection().prepareStatement(
                "UPDATE staff SET name = ?, password = ?, is_admin = ? WHERE id = ?")) {
            // Get staff name, password and admin privileges by id
            stmt.setString(1, staff.getName());
            stmt.setString(2, staff.getPassword());
            stmt.setInt(3, staff.isAdmin() ? 1 : 0);
            stmt.setInt(4, staff.getId());
            // Execute the statement
            stmt.executeUpdate();
        // Handle error(s)
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove a staff by ID
    @Override
    public void delete(int id) {
        try (
            // Prepare UPDATE DML statement
            PreparedStatement stmt = getConnection().prepareStatement(
                "DELETE FROM staff WHERE id = ?")) {
            // Get staff id
            stmt.setInt(1, id);
            // Execute the statement
            stmt.executeUpdate();
        // Handle error(s)
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }
}
