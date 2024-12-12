package com.moneycalculator.impl;

import com.google.gson.Gson;
import com.moneycalculator.config.ApplicationConfig;
import com.moneycalculator.model.Rate;
import com.moneycalculator.service.CurrencyRateService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyRateFetcher implements CurrencyRateService {

    private static final String API_URL = ApplicationConfig.getApiUrl();

    @Override
    public Rate fetchRates(String currency) throws Exception {
        String urlString = API_URL + currency;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();
            connection.disconnect();

            Gson gson = new Gson();
            return gson.fromJson(response.toString(), Rate.class);
        } else {
            throw new Exception("Failed to fetch rates: HTTP " + responseCode);
        }
    }
}
