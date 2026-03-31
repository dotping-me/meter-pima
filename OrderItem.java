public class OrderItem {
    private MenuItem item;
    private int quantity;

    // Aggregation here because menu item exists independently of OrderItem
    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem() {
        return this.item;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public float calculatePrice() {
        return this.item.getPrice() * this.quantity;
    }
}