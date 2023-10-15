package model;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerModel {

  public List<Transaction> transactions;

  public ExpenseTrackerModel() {
    transactions = new ArrayList<>(); 
  }

  public void addTransaction(Transaction t) {
    transactions.add(t);
  }

  public List<Transaction> updateTransaction(List<Transaction> t) {
    return transactions = t;
  }

  public void removeTransaction(Transaction t) {
    transactions.remove(t);
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

}