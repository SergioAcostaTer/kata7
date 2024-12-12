package com.moneycalculator.service;

import com.moneycalculator.model.Rate;

public interface CurrencyRateService {
    Rate fetchRates(String currency) throws Exception;
}
