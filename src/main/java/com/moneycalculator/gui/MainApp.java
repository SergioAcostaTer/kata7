package com.moneycalculator.gui;

import com.moneycalculator.impl.CurrencyRateFetcher;
import com.moneycalculator.service.CurrencyRateService;
import com.moneycalculator.service.MoneyConverter;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Currency Converter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 300);
            frame.setLayout(new BorderLayout());

            CurrencyRateService rateService = new CurrencyRateFetcher();
            MoneyConverter converter = new MoneyConverter();
            List<String[]> currencyDetails = loadCurrencies();

            String[] currencyDisplayNames = currencyDetails.stream()
                    .map(parts -> parts[1] + " (" + parts[0] + ")")
                    .toArray(String[]::new);

            HistoryPanel historyPanel = new HistoryPanel();
            InputPanel inputPanel = new InputPanel(rateService, converter, currencyDisplayNames, historyPanel);

            frame.add(historyPanel, BorderLayout.WEST);
            frame.add(inputPanel, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }

    private static List<String[]> loadCurrencies() {
        List<String[]> currencyDetails = new ArrayList<>();
        try (InputStream inputStream = MainApp.class.getClassLoader().getResourceAsStream("currencies.tsv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                currencyDetails.add(line.split("\t"));
            }
        } catch (Exception e) {
            System.err.println("Failed to load currencies: " + e.getMessage());
        }

        return currencyDetails;
    }
}
