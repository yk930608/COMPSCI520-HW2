import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controller.ExpenseTrackerController;
import model.AmountFilter;
import model.CategoryFilter;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;
import model.Transaction;
import controller.InputValidation;

public class ExpenseTrackerApp {

  public static void main(String[] args) {
    
    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);

    // Initialize view
    view.setVisible(true);

    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();
      
      // Call controller to add transaction
      boolean added = controller.addTransaction(amount, category);
      
      if (!added) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
      }
    });

    view.getApplyAmountFilter().addActionListener(e -> {
      // Get transaction data from view
      double filterUpperBound = view.getFilterUpperBoundField();
      double filterLowerBound = view.getFilterUpperLowerField();

      try{
        AmountFilter amountFilter = new AmountFilter(filterUpperBound, filterLowerBound);
        controller.applyFilter(amountFilter);
      } catch (RuntimeException exception) {
        JOptionPane.showMessageDialog(view, exception.getMessage());
        view.toFront();
      }
    });

    view.getApplyCategoryFilter().addActionListener(e -> {
      // Get transaction data from view
      String filterCategory = view.getFilterCategoryField();

      try{
        CategoryFilter categoryFilter = new CategoryFilter(filterCategory);
        controller.applyFilter(categoryFilter);
      } catch (RuntimeException exception) {
        JOptionPane.showMessageDialog(view, exception.getMessage());
        view.toFront();
      }
    });
  }

}