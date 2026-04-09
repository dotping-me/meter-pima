package model;

public class Food extends MenuSnackItem {
    public Food(int id, String name, float price, int amountInStock) {
        super(id, name, price, amountInStock);
    }

    // Abstract from MenuItem
    @Override
    public String getDetails() {
        return String.format("Food: %s - Rs %.2f", getName(), getPrice());
    }
}