package com.lookandlike.currencyexchange.service.impl;

import com.lookandlike.currencyexchange.data.dto.CurrencyDto;
import com.lookandlike.currencyexchange.data.entity.Currency;
import com.lookandlike.currencyexchange.exception.CurrencyException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class DefaultCurrencyServiceTest {

    private DefaultCurrencyService defaultCurrencyService = new DefaultCurrencyService();
    private CurrencyDto currencyDto;

    @Before
    public void init() {
        currencyDto = currencyDto.builder()
                .base("USD")
                .symbols(Arrays.asList("EUR", "RUB"))
                .build();
    }

    @Test
    public void successData() throws CurrencyException {
        Currency currency = defaultCurrencyService.exchange(currencyDto);
        assertNotNull(currency);
    }

    @Test
    public void sendNull() {
        boolean actual = false;
        boolean expect = true;
        try {
            defaultCurrencyService.exchange(null);
        } catch (CurrencyException e) {
            actual = true;
        }

        assertEquals(actual, expect);
    }
}
