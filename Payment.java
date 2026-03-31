public abstract class Payment {
    private float amountOfMoneyGiven;

    public Payment(float amountOfMoneyGiven) {
        this.amountOfMoneyGiven = amountOfMoneyGiven;
    }

    public float getAmountOfMoneyGiven() {
        return this.amountOfMoneyGiven;
    }

    public abstract float processPayment(float priceToBePaid);
}