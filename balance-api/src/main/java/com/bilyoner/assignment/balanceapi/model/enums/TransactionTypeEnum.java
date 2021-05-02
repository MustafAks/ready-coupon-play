package com.bilyoner.assignment.balanceapi.model.enums;

public enum TransactionTypeEnum {
    DEPOSIT("Deposit"),
    WITHDRAW("Withdraw");


    private String value;

    TransactionTypeEnum(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
