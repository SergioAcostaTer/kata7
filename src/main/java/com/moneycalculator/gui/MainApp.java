package com.moneycalculator.gui;

import com.moneycalculator.impl.CurrencyRateFetcher;
import com.moneycalculator.service.CurrencyRateService;
import com.moneycalculator.service.MoneyConverter;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Currency Converter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(675, 350);
            frame.setLayout(new BorderLayout());

            // Dependencies
            CurrencyRateService rateService = new CurrencyRateFetcher();
            MoneyConverter converter = new MoneyConverter();
            List<String[]> currencyDetails = loadCurrencyDetails("src/main/resources/currencies.tsv");

            // Components
            HistoryPanel historyPanel = new HistoryPanel();
            InputPanel inputPanel = new InputPanel(rateService, converter, historyPanel, currencyDetails);

            // Adding components to the frame
            frame.add(historyPanel.getPanel(), BorderLayout.WEST);
            frame.add(inputPanel.getPanel(), BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }

    private static List<String[]> loadCurrencyDetails(String filePath) {
        List<String[]> currencyDetails = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 2) {
                    currencyDetails.add(parts);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load currencies from file: " + e.getMessage());
        }
        return currencyDetails;
    }
}
