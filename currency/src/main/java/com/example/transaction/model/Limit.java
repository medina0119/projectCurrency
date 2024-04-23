package com.example.transaction.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(schema = "DEV", name = "LIMIT")
@Getter
@Setter
@NoArgsConstructor
public class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "account_from")
    private String accountFrom;
    @Column(name = "account_to")
    private String accountTo;
    @Column(name = "limit_sum")
    private BigDecimal limitSum;
    @Column(name = "limit_datetime")
    private Date limitDate;
    @Column(name = "limit_currency_shortname")
    private String currencyLimit;

    public Limit(String accountFrom, String accountTo, BigDecimal limitSum, Date limitDate, String currencyLimit) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.limitSum = limitSum;
        this.limitDate = limitDate;
        this.currencyLimit = currencyLimit;
    }
}
