package com.example.currency.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(schema = "DEV", name = "QUOTE")
@Getter
@Setter
@NoArgsConstructor
public class Bar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String symbol;
    private String name;
    private String exchange;
    private Date datetime;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal previous_close;
    private BigDecimal change;
    private BigDecimal percent_change;
    private boolean is_market_open;

    public Bar(String symbol, String name, String exchange, Date datetime, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, BigDecimal previous_close, BigDecimal change, BigDecimal percent_change, boolean is_market_open) {
        this.symbol = symbol;
        this.name = name;
        this.exchange = exchange;
        this.datetime = datetime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.previous_close = previous_close;
        this.change = change;
        this.percent_change = percent_change;
        this.is_market_open = is_market_open;
    }
}
