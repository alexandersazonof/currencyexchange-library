package com.lookandlike.currencyexchange.repository.impl;

import com.lookandlike.currencyexchange.data.dto.CurrencyDto;
import com.lookandlike.currencyexchange.data.entity.Rate;
import com.lookandlike.currencyexchange.repository.RateRepository;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class OpenApiRateRepository implements RateRepository {

    private final static String API = "https://api.exchangeratesapi.io/latest?base=";

    public List<Rate> getRate(CurrencyDto currencyDto) {
        sendGetRequestToApi(currencyDto);
        return null;
    }

    private String sendGetRequestToApi(CurrencyDto currencyDto) {
        try {
            URL url = new URL("https://api.exchangeratesapi.io/latest?base=USD&symbols=EUR,RUB");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();

            System.out.println(response.toString());
            return response.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
