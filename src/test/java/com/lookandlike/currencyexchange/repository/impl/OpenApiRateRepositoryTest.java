package com.lookandlike.currencyexchange.repository.impl;

import com.lookandlike.currencyexchange.data.dto.CurrencyDto;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
    public void sendSuccessGetRequest() {
        openApiRateRepository.getRate(currencyDto);
    }
}
