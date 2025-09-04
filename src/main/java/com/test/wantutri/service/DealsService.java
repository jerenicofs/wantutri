package com.test.wantutri.service;

import com.test.wantutri.model.Currency;
import com.test.wantutri.model.Deals;
import com.test.wantutri.model.TransactionInput;
import com.test.wantutri.repository.CurrencyRepository;
import com.test.wantutri.repository.DealsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class DealsService {

    private final DealsRepository dealsRepository;
    private final CurrencyRepository currencyRepository;

    private static final Map<String, BigDecimal> EXCHANGE_RATES = Map.of(
            "USD_IDR", new BigDecimal("15500.00"),
            "IDR_USD", BigDecimal.ONE.divide(new BigDecimal("15500.00"), 8, RoundingMode.HALF_UP),
            "EUR_USD", new BigDecimal("1.07"),
            "USD_EUR", BigDecimal.ONE.divide(new BigDecimal("1.07"), 8, RoundingMode.HALF_UP)
    );

    public DealsService(DealsRepository dealsRepository, CurrencyRepository currencyRepository) {
        this.dealsRepository = dealsRepository;
        this.currencyRepository = currencyRepository;
    }

    public Deals executeTransaction(TransactionInput transactionInput) {
        Currency fromCurrency = currencyRepository.findById(transactionInput.fromCurrencyCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid 'from' currency code."));
        Currency toCurrency = currencyRepository.findById(transactionInput.toCurrencyCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid 'to' currency code."));

        String rateKey = fromCurrency.getCode() + "_" + toCurrency.getCode();
        BigDecimal rate = EXCHANGE_RATES.get(rateKey);

        if (rate == null) {
            throw new IllegalArgumentException("Exchange rate not available for this pair.");
        }

        BigDecimal amountToSell = BigDecimal.valueOf(transactionInput.amount());
        BigDecimal amountToBuy = amountToSell.multiply(rate).setScale(4, RoundingMode.HALF_UP);

        return saveDeals(fromCurrency, toCurrency, amountToSell, amountToBuy, rate);
    }

    public List<Currency> getAllCurrencies(){
        return currencyRepository.findAll();
    }

    public Deals getById(String id) {
        return dealsRepository.findById(id).orElse(null);
    }

    public List<Deals> getAllTransaction(String currencyCode){
        if (currencyCode == null || currencyCode.isEmpty()) return dealsRepository.findAll();

        return dealsRepository.findByFromCurrencyCodeOrToCurrencyCode(currencyCode, currencyCode);
    }

    public Deals saveDeals(Currency fromCurrency, Currency toCurrency, BigDecimal amountToSell, BigDecimal amountToBuy, BigDecimal rate) {
        Deals deals = new Deals();
        deals.setId(UUID.randomUUID().toString());
        deals.setFromCurrency(fromCurrency);
        deals.setToCurrency(toCurrency);
        deals.setAmountSold(amountToSell);
        deals.setAmountBought(amountToBuy);
        deals.setExchangeRate(rate);

        String pss = toCurrency.getCode() + "/" + fromCurrency.getCode();
        deals.setPairsShortName(pss);
        deals.setInterfaceDate(LocalDateTime.now());

        return dealsRepository.save(deals);
    }
}
