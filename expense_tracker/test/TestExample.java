// package test;

import controller.ExpenseTrackerController;
import model.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import view.ExpenseTrackerView;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class TestExample {

    private ExpenseTrackerModel model;
    private ExpenseTrackerView view;
    private ExpenseTrackerController controller;

    @Rule
    public ExpectedException exceptionRule  = ExpectedException.none();
    @Before
    public void setup() {
        model = new ExpenseTrackerModel();
        view = new ExpenseTrackerView();
        controller = new ExpenseTrackerController(model, view);
    }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }


    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        // Perform the action: Add a transaction
        assertTrue(controller.addTransaction(50.00, "food"));

        // Post-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());

        // Check the contents of the list
        assertEquals(50.00, getTotalCost(), 0.01);
    }


    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        // Perform the action: Add and remove a transaction
        Transaction addedTransaction = new Transaction(50.00, "Groceries");
        model.addTransaction(addedTransaction);

        // Pre-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());

        // Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);

        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());

        // Check the total cost after removing the transaction
        double totalCost = getTotalCost();
        assertEquals(0.00, totalCost, 0.01);
    }

    @Test
    public void testGetTransactions() {
        final Transaction transactionOne = new Transaction(1.0, "food");
        final Transaction transactionTwo = new Transaction(10.0, "entertainment");
        final List<Transaction> expected = List.of(transactionOne, transactionTwo);
        final Transactions transactions = new Transactions(expected);
        assertThat(transactions.getTransactions(), is(expected));
    }

    @Test
    public void testCreateAmountFilter_throwsRunningTimeException() {
        double invalidLowerBound = -1.0;
        double invalidUpperBound = 1001.0;
        double validUpperBound = 95.0;
        double validLowerBound = 1.0;
        double higherLowerBound = 96.0;
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("invalid upperBound");
        new AmountFilter(invalidUpperBound, validLowerBound);
        exceptionRule.expectMessage("invalid lowerBound");
        new AmountFilter(validUpperBound, invalidLowerBound);
        exceptionRule.expectMessage("lowerBound needs be lower than upperbound");
        new AmountFilter(validUpperBound, higherLowerBound);
    }

    @Test
    public void testCreateCategoryFilter_throwsRunningTimeException() {
        String invalidCategory = "drink";
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("invalid category");
        new CategoryFilter(invalidCategory);
    }

    @Test
    public void testAmountFilter_Filter(){
        final Transaction transactionOne = new Transaction(2.0, "food");
        final Transaction transactionTwo = new Transaction(10.0, "entertainment");
        final List<Transaction> transactions = List.of(transactionOne, transactionTwo);
        final List<Transaction> expected = List.of(transactionOne);
        final AmountFilter amountFilter = new AmountFilter(9.0, 1.0);
        assertThat(amountFilter.filter(transactions), is(expected));
    }

    @Test
    public void testCategoryFilter_Filter(){
        final Transaction transactionOne = new Transaction(2.0, "food");
        final Transaction transactionTwo = new Transaction(10.0, "entertainment");
        final List<Transaction> transactions = List.of(transactionOne, transactionTwo);
        final List<Transaction> expected = List.of(transactionTwo);
        final CategoryFilter amountFilter = new CategoryFilter("entertainment");
        assertThat(amountFilter.filter(transactions), is(expected));
    }
}