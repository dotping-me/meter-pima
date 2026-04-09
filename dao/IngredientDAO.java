package dao;

import java.sql.*;
import java.util.*;
import model.Ingredient;

public class IngredientDAO extends BaseDAO implements DAO<Ingredient> {

    // NOTE: BaseDAO already has a getConnection()

    // Creates an ingredient
    @Override
    public void create(Ingredient ingredient) {
        System.out.println("New ingredient created");

        try (
            PreparedStatement stmt = getConnection().prepareStatement(
                "INSERT INTO Ingredient(name, quantity) VALUES(?, ?)"
            );
        ) {

            stmt.setString(1, ingredient.getName());
            stmt.setInt(2, ingredient.getAmountInStock());
            stmt.executeUpdate();

        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    // Retrieves an ingredient by ID
    @Override
    public Ingredient getById(int id) {
        try (
            PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT * FROM Ingredient WHERE id = ?"
            );
        ) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Ingredient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("quantity")
                );
            }

        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return null; // Nothing found
    }

    public Ingredient getByName(String name) {
        try (
            PreparedStatement stmt = getConnection().prepareStatement(
                "SELECT * FROM Ingredient WHERE name = ?"
            );
        ) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Ingredient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("quantity")
                );
            }

        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return null; // Nothing found
    }

    // Return all ingredients
    @Override
    public List<Ingredient> getAll() {
        List<Ingredient> ingredients = new ArrayList<>();

        try (
            Statement stmt = getConnection().createStatement();
        ) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM Ingredient");
            while (rs.next()) { 
                ingredients.add(
                    new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity")
                    )
                );
            }

        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return ingredients; // Will be null if nothing found
    }

    // Updates quantity for an ingredient (Hardcoded updatable fields)
    @Override
    public void update(Ingredient ingredient) {
        try (
            PreparedStatement stmt = getConnection().prepareStatement(
                "UPDATE Ingredient SET quantity = ? WHERE id = ?"
            );
        ) {

            stmt.setInt(1, ingredient.getAmountInStock());
            stmt.setInt(2, ingredient.getIngredientId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove an ingredient by ID
    @Override
    public void delete(int id) {
        try (
            PreparedStatement stmt = getConnection().prepareStatement(
                "DELETE FROM Ingredient WHERE id = ?");
        ) {
    
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }

}