package io.vertx.bean;


public class Balance {

    private Currency currency;
    private double amount;

    public Balance(){
        this.currency = currency;
        this.amount = amount;

    }

    public Balance(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
