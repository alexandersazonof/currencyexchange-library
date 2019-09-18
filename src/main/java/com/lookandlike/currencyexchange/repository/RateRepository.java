package com.lookandlike.currencyexchange.repository;

import com.lookandlike.currencyexchange.data.dto.CurrencyDto;
import com.lookandlike.currencyexchange.data.entity.Rate;
import com.lookandlike.currencyexchange.exception.CurrencyException;

import java.util.List;

public interface RateRepository {
    List<Rate> getRate(CurrencyDto currencyDto) throws CurrencyException;
}
