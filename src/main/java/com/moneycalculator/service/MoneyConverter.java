package com.moneycalculator.service;

import com.moneycalculator.model.Rate;

public class MoneyConverter {

    public double convert(Rate rate, String targetCurrency, double amount) throws IllegalArgumentException {
        if (rate.getRates().containsKey(targetCurrency)) {
            double conversionRate = rate.getRates().get(targetCurrency);
            return amount * conversionRate;
        } else {
            throw new IllegalArgumentException("Currency not found: " + targetCurrency);
        }
    }
}
