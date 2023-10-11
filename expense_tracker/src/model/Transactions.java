package model;

import java.util.List;

public class Transactions {
    private final List<Transaction> transactions;
    //Apply immutability on the list of transactions when the getter method is invoked.
    public Transactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
