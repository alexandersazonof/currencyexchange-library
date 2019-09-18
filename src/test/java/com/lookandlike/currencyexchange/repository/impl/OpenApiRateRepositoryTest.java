package com.lookandlike.currencyexchange.repository.impl;

import com.lookandlike.currencyexchange.data.dto.CurrencyDto;
import com.lookandlike.currencyexchange.data.entity.Rate;
import com.lookandlike.currencyexchange.exception.CurrencyException;
import org.junit.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class OpenApiRateRepositoryTest {

    private OpenApiRateRepository openApiRateRepository = new OpenApiRateRepository();
    private CurrencyDto currencyDto;

    @Before
    public void init() {
        currencyDto = currencyDto.builder()
                .base("USD")
                .symbols(Arrays.asList("EUR", "RUB"))
                .build();
    }

    @Test
    public void sendSuccessGetRequest() throws CurrencyException {
        List<Rate> rates = openApiRateRepository.getRate(currencyDto);
        boolean actual = rates.size() > 0;
        boolean expeсt = true;
        assertEquals(actual, expeсt);
    }
}
