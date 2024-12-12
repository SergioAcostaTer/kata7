package com.moneycalculator.gui;

import com.moneycalculator.commands.ExchangeCommand;
import com.moneycalculator.config.ApplicationConfig;
import com.moneycalculator.service.CurrencyRateService;
import com.moneycalculator.service.MoneyConverter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InputPanel {
    private final JPanel panel;
    private final CurrencyRateService rateService;
    private final MoneyConverter converter;
    private final HistoryPanel historyPanel;
    private final List<String[]> currencyDetails;

    public InputPanel(CurrencyRateService rateService, MoneyConverter converter, HistoryPanel historyPanel, List<String[]> currencyDetails) {
        this.rateService = rateService;
        this.converter = converter;
        this.historyPanel = historyPanel;
        this.currencyDetails = currencyDetails;

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(10);
        JLabel fromCurrencyLabel = new JLabel("From Currency:");
        JComboBox<String> fromCurrencyDropdown = new JComboBox<>(getCurrencyDisplayNames());
        JLabel toCurrencyLabel = new JLabel("To Currency:");
        JComboBox<String> toCurrencyDropdown = new JComboBox<>(getCurrencyDisplayNames());
        JButton convertButton = new JButton("Convert");
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Set default currencies
        setDefaultCurrencySelection(fromCurrencyDropdown, toCurrencyDropdown);

        // Layout components
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(amountLabel, gbc);

        gbc.gridx = 1;
        panel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(fromCurrencyLabel, gbc);

        gbc.gridx = 1;
        panel.add(fromCurrencyDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(toCurrencyLabel, gbc);

        gbc.gridx = 1;
        panel.add(toCurrencyDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        convertButton.setPreferredSize(new Dimension(150, 25));
        panel.add(convertButton, gbc);

        gbc.gridy = 4;
        panel.add(resultLabel, gbc);

        // Conversion logic
        convertButton.addActionListener(e -> {
            resultLabel.setText("");
            SwingUtilities.invokeLater(() -> {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    String fromCurrency = getCurrencyCode((String) fromCurrencyDropdown.getSelectedItem());
                    String toCurrency = getCurrencyCode((String) toCurrencyDropdown.getSelectedItem());

                    if (fromCurrency == null || toCurrency == null) {
                        resultLabel.setText("Please select both currencies.");
                        return;
                    }

                    ExchangeCommand command = new ExchangeCommand(rateService, converter, fromCurrency, toCurrency, amount);
                    command.execute();

                    double convertedAmount = command.getConvertedAmount();
                    resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, fromCurrency, convertedAmount, toCurrency));

                    String historyEntry = String.format("<html><b>%s %s</b> -> <b>%s %s</b></html>", amount, fromCurrency, convertedAmount, toCurrency);
                    historyPanel.addHistoryCard(historyEntry);
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid amount. Please enter a valid number.");
                } catch (Exception ex) {
                    resultLabel.setText("Conversion failed: " + ex.getMessage());
                }
            });
        });
    }

    private void setDefaultCurrencySelection(JComboBox<String> fromCurrencyDropdown, JComboBox<String> toCurrencyDropdown) {
        String baseCurrencyDefault = ApplicationConfig.getBaseCurrency();
        String toCurrencyDefault = ApplicationConfig.getToCurrency();

        if (baseCurrencyDefault != null) {
            for (int i = 0; i < fromCurrencyDropdown.getItemCount(); i++) {
                if (fromCurrencyDropdown.getItemAt(i).contains(baseCurrencyDefault)) {
                    fromCurrencyDropdown.setSelectedIndex(i);
                    break;
                }
            }
        }

        if (toCurrencyDefault != null) {
            for (int i = 0; i < toCurrencyDropdown.getItemCount(); i++) {
                if (toCurrencyDropdown.getItemAt(i).contains(toCurrencyDefault)) {
                    toCurrencyDropdown.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private String[] getCurrencyDisplayNames() {
        return currencyDetails.stream().map(parts -> parts[1] + " (" + parts[0] + ")").toArray(String[]::new);
    }

    private String getCurrencyCode(String displayName) {
        return currencyDetails.stream()
                .filter(parts -> displayName.contains(parts[1]))
                .map(parts -> parts[0])
                .findFirst()
                .orElse(null);
    }

    public JPanel getPanel() {
        return panel;
    }
}
