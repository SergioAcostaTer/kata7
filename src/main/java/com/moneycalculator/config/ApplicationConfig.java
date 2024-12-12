package com.moneycalculator.config;

public class ApplicationConfig {

    private static final String BASE_CURRENCY = "EUR";
    private static final String TO_CURRENCY = "USD";
    private static final String API_URL = "https://api.fxratesapi.com/latest?base=";

    public static String getBaseCurrency() {
        return BASE_CURRENCY;
    }

    public static String getApiUrl() {
        return API_URL;
    }

    public static String getToCurrency() {
        return TO_CURRENCY;
    }
}
