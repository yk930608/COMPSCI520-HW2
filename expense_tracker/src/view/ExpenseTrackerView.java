package view;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.table.DefaultTableModel;

import controller.InputValidation;

import java.awt.*;
import java.text.NumberFormat;

import model.Transaction;
import java.util.List;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;
  private JFormattedTextField filterUpperBoundField;
  private JFormattedTextField filterLowerBoundField;
  private JButton applyAmountFilter;
  private JTextField filterCategoryField;
  private JButton applyCategoryFilter;
  private JButton undoBtn;

  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

    addTransactionBtn = new JButton("Add Transaction");

    applyAmountFilter = new JButton("filter by amount");

    applyCategoryFilter = new JButton("filter by category");

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    JLabel amountUpperBoundLabel = new JLabel("Upper Amount:");

    JLabel amountLowerBoundLabel = new JLabel("Lower Amount:");


    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);
    filterUpperBoundField = new JFormattedTextField(format);
    filterUpperBoundField.setColumns(10);
    filterLowerBoundField = new JFormattedTextField(format);
    filterLowerBoundField.setColumns(10);
    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);
    JLabel filterCategoryLabel = new JLabel("Filter Category:");
    filterCategoryField = new JTextField(10);

    // Create table
    transactionsTable = new JTable(model);
  
    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);

    JPanel filterPanel = new JPanel();
    filterPanel.add(amountLowerBoundLabel);
    filterPanel.add(filterLowerBoundField);
    filterPanel.add(amountUpperBoundLabel);
    filterPanel.add(filterUpperBoundField);
    filterPanel.add(applyAmountFilter);
    filterPanel.add(filterCategoryLabel);
    filterPanel.add(filterCategoryField);
    filterPanel.add(applyCategoryFilter);


    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);
    buttonPanel.add(undoBtn);
    // Add panels to frame
    add(inputPanel, BorderLayout.NORTH);
    add(filterPanel, BorderLayout.WEST);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
    add(buttonPanel, BorderLayout.SOUTH);
  
    // Set frame properties
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  
  }

  public void refreshTable(List<Transaction> transactions) {
      // Clear existing rows
      model.setRowCount(0);
      // Get row count
      int rowNum = model.getRowCount();
      double totalCost=0;
      // Calculate total cost
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()}); 
      }
        // Add total row
        Object[] totalRow = {"Total", null, null, totalCost};
        model.addRow(totalRow);
  
      // Fire table update
      transactionsTable.updateUI();
  
    }  
  

  
  
  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }

  public JButton getUndoBtn() {
    return undoBtn;
  }

  public JButton getApplyAmountFilter() {
    return applyAmountFilter;
  }

  public JButton getApplyCategoryFilter() {
    return applyCategoryFilter;
  }

  public DefaultTableModel getTableModel() {
    return model;
  }

  // Other view methods
  public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(amountField.getText());
    return amount;
    }
  }

  public double getFilterUpperBoundField() {
    if(filterUpperBoundField.getText().isEmpty()) {
      return 1000.0;
    }else {
      double amount = Double.parseDouble(filterUpperBoundField.getText());
      return amount;
    }
  }

  public double getFilterUpperLowerField() {
    if(filterLowerBoundField.getText().isEmpty()) {
      return 0.01;
    }else {
      double amount = Double.parseDouble(filterLowerBoundField.getText());
      return amount;
    }
  }

  public String getFilterCategoryField() {
    return filterCategoryField.getText();
  }
  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  public String getCategoryField() {
    return categoryField.getText();
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  public int[] getUserSelection() {
    return transactionsTable.getSelectedRows();
  }
}
