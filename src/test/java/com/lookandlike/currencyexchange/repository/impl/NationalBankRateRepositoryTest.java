package com.lookandlike.currencyexchange.repository.impl;

import com.lookandlike.currencyexchange.data.dto.CurrencyDto;
import com.lookandlike.currencyexchange.data.entity.Rate;
import com.lookandlike.currencyexchange.exception.CurrencyException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NationalBankRateRepositoryTest {

    private NationalBankRateRepository nationalBankRateRepository = new NationalBankRateRepository();
    private CurrencyDto currencyDto;

    @Before
    public void init() {
        currencyDto = currencyDto.builder()
                .base("USD")
                .symbols(Arrays.asList("EUR", "RUB"))
                .build();
    }

    @Test
    public void sendSuccessRequest() throws CurrencyException {
        List<Rate> rates = nationalBankRateRepository.getRate(currencyDto);
        boolean actual = rates.size() > 0;
        boolean expect = true;
        assertEquals(actual, expect);
    }
}
