package com.moneycalculator.gui;

import com.moneycalculator.commands.ExchangeCommand;
import com.moneycalculator.service.CurrencyRateService;
import com.moneycalculator.service.MoneyConverter;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {

    private final CurrencyRateService rateService;
    private final MoneyConverter converter;
    private final JComboBox<String> fromCurrencyDropdown;
    private final JComboBox<String> toCurrencyDropdown;
    private final JTextField amountField;
    private final JLabel resultLabel;
    private final HistoryPanel historyPanel;

    public InputPanel(CurrencyRateService rateService, MoneyConverter converter, String[] currencyDisplayNames, HistoryPanel historyPanel) {
        this.rateService = rateService;
        this.converter = converter;
        this.historyPanel = historyPanel;

        setLayout(new GridBagLayout()); // Centered within available space
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);
        JLabel fromCurrencyLabel = new JLabel("From Currency:");
        fromCurrencyDropdown = new JComboBox<>(currencyDisplayNames);
        JLabel toCurrencyLabel = new JLabel("To Currency:");
        toCurrencyDropdown = new JComboBox<>(currencyDisplayNames);
        JButton convertButton = new JButton("Convert");
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        setDefaultSelections();

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(amountLabel, gbc);

        gbc.gridx = 1;
        add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(fromCurrencyLabel, gbc);

        gbc.gridx = 1;
        add(fromCurrencyDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(toCurrencyLabel, gbc);

        gbc.gridx = 1;
        add(toCurrencyDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        convertButton.setPreferredSize(new Dimension(150, 25));
        add(convertButton, gbc);

        gbc.gridy = 4;
        add(resultLabel, gbc);

        convertButton.addActionListener(e -> handleConvertAction());
    }

    private void setDefaultSelections() {
        String baseCurrencyDefault = "USD";
        String toCurrencyDefault = "EUR";

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

    private void handleConvertAction() {
        resultLabel.setText("");
        SwingUtilities.invokeLater(() -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String fromCurrency = extractCurrencyCode((String) fromCurrencyDropdown.getSelectedItem());
                String toCurrency = extractCurrencyCode((String) toCurrencyDropdown.getSelectedItem());

                if (fromCurrency == null || toCurrency == null) {
                    resultLabel.setText("Please select both currencies.");
                    return;
                }

                ExchangeCommand command = new ExchangeCommand(rateService, converter, fromCurrency, toCurrency, amount);
                command.execute();
                historyPanel.addHistoryCard(command);

                double convertedAmount = command.getConvertedAmount();
                resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, fromCurrency, convertedAmount, toCurrency));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid amount. Please enter a valid number.");
            } catch (Exception ex) {
                resultLabel.setText("Conversion failed: " + ex.getMessage());
            }
        });
    }

    private String extractCurrencyCode(String displayName) {
        return displayName.split(" \\(")[1].replace(")", "");
    }
}
