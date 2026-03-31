public class Manager extends Staff implements CanRestock {
    public Manager(int staffId, String name) {
        super(staffId, name);
    }

    @Override
    public void restock(Ingredient ingredient, int amount) {
        if (amount <= 0) return;
        ingredient.restock(amount); // Restocking logic
    }
}