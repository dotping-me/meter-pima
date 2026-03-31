public class Ingredient {
    private int ingredientId;
    private String name;
    private int amountInStock;

    public Ingredient(int ingredientId, String name, int amountInStock) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.amountInStock = amountInStock;
    }

    public int getIngredientId() {
        return this.ingredientId;
    }

    public String getName() {
        return this.name;
    }

    public int getAmountInStock() {
        return this.amountInStock;
    }

    // Increases the quantity of this ingredient in stock
    public void restock(int amount) {
        this.amountInStock += amount;
    }

    // Decreases the quantity of this ingredient in stock
    public boolean use(int amount) {
        if (this.amountInStock < amount) return false;

        // Consumes ingredient (also reflect in database)
        this.amountInStock -= amount;

        return true;
    }
}