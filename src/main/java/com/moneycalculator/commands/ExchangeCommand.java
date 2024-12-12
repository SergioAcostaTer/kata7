package com.moneycalculator.commands;

import com.moneycalculator.model.Rate;
import com.moneycalculator.service.CurrencyRateService;
import com.moneycalculator.service.MoneyConverter;

public class ExchangeCommand implements Command {

    private final CurrencyRateService rateService;
    private final MoneyConverter converter;
    private final String sourceCurrency;
    private final String targetCurrency;
    private final double amount;
    private double convertedAmount;

    public ExchangeCommand(CurrencyRateService rateService, MoneyConverter converter, String sourceCurrency, String targetCurrency, double amount) {
        this.rateService = rateService;
        this.converter = converter;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
    }

    @Override
    public void execute() {
        try {
            Rate rate = rateService.fetchRates(sourceCurrency);
            convertedAmount = converter.convert(rate, targetCurrency, amount);
            System.out.println(amount + " " + sourceCurrency + " to " + targetCurrency + " is: " + convertedAmount);
            CommandHistory.addCommand(this);
        } catch (Exception e) {
            System.err.println("Failed to execute command: " + e.getMessage());
        }
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public double getConvertedAmount() {
        return Math.round(convertedAmount * 100.0) / 100.0;
    }

}
