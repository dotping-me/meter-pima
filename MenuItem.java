// MenuItem is abstract because there won't ever be a plain MenuItem object,
// only Food and Drink objects

// TODO: Think of abstract methods this could have

public abstract class MenuItem {
    private int itemId;
    private String name;
    private float price;

    public MenuItem(int itemId, String name, float price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }

    public int getMenuItemId() {
        return this.itemId;
    }

    public String getName() {
        return this.name;
    }

    public float getPrice() {
        return this.price;
    }
}