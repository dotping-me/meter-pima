// Payment in cash is simple, just take the money and return the change

public class CashPayment extends Payment {
    public CashPayment(float amountOfMoneyGiven) {
        super(amountOfMoneyGiven);
    }

    @Override
    public float processPayment(float priceToBePaid) {
        if (priceToBePaid > this.getAmountOfMoneyGiven()) {
            return -1;
        }

        return this.getAmountOfMoneyGiven() - priceToBePaid;
    }
}