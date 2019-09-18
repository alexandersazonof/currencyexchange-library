package com.lookandlike.currencyexchange.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lookandlike.currencyexchange.data.dao.NationalBankCurrencyDao;
import com.lookandlike.currencyexchange.data.dto.CurrencyDto;
import com.lookandlike.currencyexchange.data.entity.Rate;
import com.lookandlike.currencyexchange.exception.CurrencyException;
import com.lookandlike.currencyexchange.repository.RateRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NationalBankRateRepository implements RateRepository {

    private final static String API = "http://www.nbrb.by/API/ExRates/Rates/";

    @Override
    public List<Rate> getRate(CurrencyDto currencyDto) throws CurrencyException {
        List<Rate> rates = new ArrayList<>();
        for (String symbol :currencyDto.getSymbols()) {
            NationalBankCurrencyDao bankCurrencyDao = getData(symbol);
            rates.add(
                    Rate.builder()
                            .symbol(symbol)
                            .value(bankCurrencyDao.getRate() / bankCurrencyDao.getScale())
                            .build()
            );
        }
        return rates;
    }

    private NationalBankCurrencyDao getData(String symbol) throws CurrencyException {
        try {
            URL url = new URL(buildLink(symbol));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return parseJson(response.toString());
        } catch (MalformedURLException e) {
            throw new CurrencyException(e.getMessage(), e);
        } catch (IOException e) {
            throw new CurrencyException(e.getMessage(), e);
        }
    }

    private NationalBankCurrencyDao parseJson(String json) throws CurrencyException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            NationalBankCurrencyDao value = mapper.readValue(json, NationalBankCurrencyDao.class);
            System.out.println(value);
            return value;
        } catch (IOException e) {
            throw new CurrencyException(e.getMessage(), e);
        }
    }

    private String buildLink(String symbol) {
        return API + symbol + "?paramMode=2";
    }
}
