package com.test.wantutri.repository;

import com.test.wantutri.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
