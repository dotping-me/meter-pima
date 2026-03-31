// Card payment is much simpler, the exact amount is just taken from the balance

public class CardPayment extends Payment {
    private final String cardId;
    private float balance;

    // A whole different constructor from Payment
    public CardPayment(String cardId, float balance) {
        super(balance); // Considering full balance for payment

        this.cardId = cardId;
        this.balance = balance;
    }

    public String getCardId() {
        return this.cardId;
    }

    // Standardised to use this so that "updates" do not happen in two different
    // places for less headaches in the future, I hope.
    public float getBalance() {
        return this.getAmountOfMoneyGiven();
    }

    @Override
    public float processPayment(float priceToBePaid) {
        this.balance = this.getAmountOfMoneyGiven() - priceToBePaid;
        return 0; // No change returned
    }
}