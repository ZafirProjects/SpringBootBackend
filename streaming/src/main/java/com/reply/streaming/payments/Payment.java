package com.reply.streaming.payments;

public class Payment {
    private String CreditCardNumber;
    private int amount;

    public Payment(String creditCardNumber, int amount) {
        CreditCardNumber = creditCardNumber;
        this.amount = amount;
    }

    public String getCreditCardNumber() {
        return CreditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        CreditCardNumber = creditCardNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
