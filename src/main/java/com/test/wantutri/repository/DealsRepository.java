package com.test.wantutri.repository;

import com.test.wantutri.model.Deals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealsRepository extends JpaRepository<Deals,String> {

    List<Deals> findByFromCurrencyCodeOrToCurrencyCode(String fromCurrencyCode, String toCurrencyCode);

}
