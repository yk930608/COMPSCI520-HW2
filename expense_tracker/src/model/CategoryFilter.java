package model;

import controller.InputValidation;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilter implements TransactionFilter{

    private final String category;

    public CategoryFilter(String category) throws RuntimeException{
        if(!InputValidation.isValidCategory(category)) {
            throw new RuntimeException("invalid category");
        }
        this.category = category;
    }
    @Override
    public List<Transaction> filter(List<Transaction> transactionList) {
        return transactionList.stream()
                .filter(transaction -> transaction.getCategory().equals(this.category))
                .collect(Collectors.toList());
    }
}
