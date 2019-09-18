package com.lookandlike.currencyexchange.service.impl;

import com.lookandlike.currencyexchange.data.dto.CurrencyDto;
import com.lookandlike.currencyexchange.data.entity.Currency;
import com.lookandlike.currencyexchange.data.entity.Rate;
import com.lookandlike.currencyexchange.exception.CurrencyException;
import com.lookandlike.currencyexchange.repository.impl.NationalBankRateRepository;
import com.lookandlike.currencyexchange.repository.impl.OpenApiRateRepository;
import com.lookandlike.currencyexchange.service.CurrencyService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultCurrencyService implements CurrencyService {

    private OpenApiRateRepository openApiRateRepository = new OpenApiRateRepository();
    private NationalBankRateRepository nationalBankRateRepository = new NationalBankRateRepository();
    private final static String BELARUS_SYMBOL = "BYN";

    public Currency exchange(CurrencyDto currencyDto) throws CurrencyException {
        try {
            currencyDto.setBase(currencyDto.getBase().toUpperCase());
            currencyDto.getSymbols().replaceAll(String::toUpperCase);
        } catch (NullPointerException e) {
            throw new CurrencyException(e.getMessage(), e);
        }

        List<Rate> rates = new ArrayList<>();

        if (currencyDto.getBase().equals(BELARUS_SYMBOL)) {
            nationalBankRateRepository.getRate(currencyDto).forEach(item -> {
                item.setValue(1 / item.getValue());
                rates.add(item);
            });
        } else  {
            if (currencyDto.getSymbols().contains(BELARUS_SYMBOL)) {

                currencyDto.getSymbols().remove(BELARUS_SYMBOL);
                openApiRateRepository.getRate(currencyDto).forEach(item -> rates.add(item));

                Rate temp = nationalBankRateRepository.getRate(
                        CurrencyDto.builder()
                                .base(BELARUS_SYMBOL)
                                .symbols(Arrays.asList(currencyDto.getBase()))
                                .build()
                ).get(0);

                rates.add(
                        Rate.builder()
                                .symbol(BELARUS_SYMBOL)
                                .value(temp.getValue())
                                .build()
                );
            } else {
                openApiRateRepository.getRate(currencyDto).forEach(item -> rates.add(item));
            }
        }

        return Currency.builder()
                .rates(rates)
                .base(currencyDto.getBase())
                .build();
    }
}
