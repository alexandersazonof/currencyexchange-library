package com.lookandlike.currencyexchange.service;

import com.lookandlike.currencyexchange.data.dto.CurrencyDto;
import com.lookandlike.currencyexchange.data.entity.Currency;

import java.util.List;

public interface CurrencyService {
    Currency exchange(CurrencyDto currencyDto);
}
