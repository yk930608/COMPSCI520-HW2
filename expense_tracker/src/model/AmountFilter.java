package model;

import controller.InputValidation;

import java.util.List;
import java.util.stream.Collectors;

public class AmountFilter implements TransactionFilter {
    private final double upperBound;
    private final double lowerBound;

    public AmountFilter(double upperBound, double lowerBound) throws RuntimeException {
        if (!InputValidation.isValidAmount(upperBound)) {
            throw new RuntimeException("invalid upperBound");
        }
        if (!InputValidation.isValidAmount(lowerBound)) {
            throw new RuntimeException("invalid lowerBound");
        }
        if (lowerBound > upperBound) {
            throw new RuntimeException("lowerBound needs be lower than upperbound");
        }
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactionList) {
        return transactionList.stream().
                filter(transaction -> transaction.getAmount() <= this.upperBound
                        && transaction.getAmount() >= this.lowerBound
                ).collect(Collectors.toList());
    }
}
