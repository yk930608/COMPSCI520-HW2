package controller;

import model.ExpenseTrackerModel;
import model.Transaction;
import model.TransactionFilter;
import view.ExpenseTrackerView;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class ExpenseTrackerController {

    private final Color GREEN = new Color(173, 255, 168);
    private final ExpenseTrackerModel model;
    private final ExpenseTrackerView view;

    public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
        this.model = model;
        this.view = view;

        // Set up view event handlers
    }

    public void refresh() {

        // Get transactions from model
        List<Transaction> transactions = model.getTransactions();

        // Pass to view
        view.refreshTable(transactions);

    }

    public boolean addTransaction(double amount, String category) {
        if (!InputValidation.isValidAmount(amount)) {
            return false;
        }
        if (!InputValidation.isValidCategory(category)) {
            return false;
        }

        Transaction t = new Transaction(amount, category);
        model.addTransaction(t);
        view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
        refresh();
        return true;
    }

    //   Other controller methods
    public void applyFilter(TransactionFilter transactionFilter) {
        List<Transaction> currentTransactions = model.getTransactions();
        view.refreshTable(currentTransactions);
        List<Transaction> filteredTransactions = transactionFilter.filter(currentTransactions);
        for (int i = 0; i < currentTransactions.size(); i++) {
            if (isTransactionIn(currentTransactions.get(i), filteredTransactions)) {
                view.getTransactionsTable().addRowSelectionInterval(i,i);
            }
        }
        view.getTransactionsTable().setSelectionBackground(GREEN);
        view.getTransactionsTable().updateUI();
    }

    private boolean isTransactionIn(Transaction transaction, List<Transaction> filteredTransactions) {
        for (Transaction filteredTransaction : filteredTransactions) {
            if (transaction.equals(filteredTransaction)) {
                return true;
            }
        }
        return false;
    }
}