package com.example.transaction.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(schema = "DEV", name = "TRANSACTION")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "account_from")
    private String accountFrom;
    @Column(name = "account_to")
    private String accountTo;
    @Column(name = "currency_shortname")
    private String currencyCode;
    @Column(name = "sum")
    private BigDecimal amount;
    @Column(name = "expense_category")
    private String expenseCategory;
    @Column(name = "transaction_date")
    private Date transactionDate;

    public Transaction(String accountFrom, String accountTo, String currencyCode, BigDecimal amount, String expenseCategory, Date transactionDate) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.expenseCategory = expenseCategory;
        this.transactionDate = transactionDate;
    }

}
