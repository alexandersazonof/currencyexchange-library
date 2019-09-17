package com.lookandlike.currencyexchange.service.impl;

import com.lookandlike.currencyexchange.data.dto.CurrencyDto;
import com.lookandlike.currencyexchange.data.entity.Currency;
import com.lookandlike.currencyexchange.repository.impl.OpenApiRateRepository;
import com.lookandlike.currencyexchange.service.CurrencyService;

import java.util.List;

public class DefaultCurrencyService implements CurrencyService {

    private OpenApiRateRepository openApiRateRepository;

    public DefaultCurrencyService() {
        openApiRateRepository = new OpenApiRateRepository();
    }

    public Currency exchange(CurrencyDto currencyDto) {
        openApiRateRepository.getRate(currencyDto);
        return null;
    }
}
