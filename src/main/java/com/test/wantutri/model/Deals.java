package com.test.wantutri.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "deals")
public class Deals {
    @Id
    private String id;

    @Column(name = "amount_sold")
    private BigDecimal amountSold;

    @Column(name = "amount_bought")
    private BigDecimal amountBought;

    private BigDecimal exchangeRate;
    private String pairsShortName;
    private LocalDateTime interfaceDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_currency_code")
    private Currency fromCurrency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_currency_code")
    private Currency toCurrency;
}
