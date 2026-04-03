import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderItem> orderItems;
    private Payment linkedPayingCustomerPayment; // Can be Cash or Card

    public Order(Payment linkedPayingCustomerPayment) {
        this.orderItems = new ArrayList<OrderItem>();
        this.linkedPayingCustomerPayment = linkedPayingCustomerPayment;
    }

    public OrderItem getOrder(int index) {
        return this.orderItems.get(index);
    }

    // Quite a long name I know but I hope it makes sense
    // It is basically a referrence to the customer the order is for
    public Payment getLinkedPayingCustomerPayment() {
        return this.linkedPayingCustomerPayment;
    }

    // Composition as OrderItem cannot exist if the whole Order is deleted
    public void addOrderItem(MenuItem item, int quantity) {
        this.orderItems.add(new OrderItem(item, quantity));
    }
}