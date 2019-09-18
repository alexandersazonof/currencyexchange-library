package com.lookandlike.currencyexchange.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lookandlike.currencyexchange.data.dao.OpenApiCurrencyDao;
import com.lookandlike.currencyexchange.data.dto.CurrencyDto;
import com.lookandlike.currencyexchange.data.entity.Rate;
import com.lookandlike.currencyexchange.exception.CurrencyException;
import com.lookandlike.currencyexchange.repository.RateRepository;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OpenApiRateRepository implements RateRepository {

    private final static String API = "https://api.exchangeratesapi.io/latest?base=";

    public List<Rate> getRate(CurrencyDto currencyDto) throws CurrencyException {
        String json = sendGetRequestToApi(currencyDto);
        OpenApiCurrencyDao openApiCurrencyDao = parseJson(json);

        List<Rate> rates = new ArrayList<Rate>();

        currencyDto.getSymbols().forEach(symbol -> {
            rates.add(
                    Rate.builder()
                            .symbol(symbol)
                            .value(openApiCurrencyDao.getRates().get(symbol).floatValue())
                            .build()
            );
        });

        return rates;
    }

    private String sendGetRequestToApi(CurrencyDto currencyDto) throws CurrencyException {
        try {
            URL url = new URL(buildLink(currencyDto));
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

            return response.toString();
        } catch (MalformedURLException e) {
            throw new CurrencyException(e.getMessage(), e);
        } catch (IOException e) {
            throw new CurrencyException(e.getMessage(), e);
        }
    }

    private OpenApiCurrencyDao parseJson(String json) throws CurrencyException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            OpenApiCurrencyDao value = mapper.readValue(json, OpenApiCurrencyDao.class);
            return value;
        } catch (IOException e) {
            throw new CurrencyException(e.getMessage(), e);
        }
    }


    private String buildLink(CurrencyDto currencyDto) {
        StringBuffer symbols = new StringBuffer("");
        int length = currencyDto.getSymbols().size();
        AtomicInteger index = new AtomicInteger(1);

        currencyDto.getSymbols().forEach(s -> {

            if (index.get() == length) {
                symbols.append(s);
            } else {
                index.incrementAndGet();
                symbols.append(s + ",");
            }

        });
        return API + currencyDto.getBase() + "&symbols=" + symbols.toString();
    }
}
