package com.test.wantutri.controller;

import com.test.wantutri.model.Currency;
import com.test.wantutri.model.Deals;
import com.test.wantutri.model.TransactionInput;
import com.test.wantutri.repository.CurrencyRepository;
import com.test.wantutri.repository.DealsRepository;
import com.test.wantutri.service.DealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DealsController {

    private final DealsService dealsService;
    public DealsController(DealsService dealsService) {
        this.dealsService= dealsService;

    }

    @QueryMapping
    public Deals transaction(@Argument String id) {
        return dealsService.getById(id);
    }

    @QueryMapping
    public List<Deals> listTransaction(@Argument String currencyCode){
        return dealsService.getAllTransaction(currencyCode);
    }

    @QueryMapping
    public List<Currency> currencies(){
        return dealsService.getAllCurrencies();
    }

    @MutationMapping
    public Deals executeTransaction(@Argument TransactionInput input) {
        return dealsService.executeTransaction(input);
    }
}
