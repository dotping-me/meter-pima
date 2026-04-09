package model;

public class Manager extends Staff implements CanRestock {
    
    public Manager(int id, String name, String password) {
        super(id, name, password, true);
    }

    @Override
    public void restock(Ingredient ingredient, int amount) {
        if (amount <= 0) return;
        ingredient.restock(amount); // Restocking logic
    }
}