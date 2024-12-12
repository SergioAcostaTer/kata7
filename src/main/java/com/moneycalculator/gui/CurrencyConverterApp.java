package com.moneycalculator.gui;

import com.moneycalculator.commands.ExchangeCommand;
import com.moneycalculator.config.ApplicationConfig;
import com.moneycalculator.impl.CurrencyRateFetcher;
import com.moneycalculator.service.CurrencyRateService;
import com.moneycalculator.service.MoneyConverter;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrencyConverterApp {

    private final CurrencyRateService rateService = new CurrencyRateFetcher();
    private final MoneyConverter converter = new MoneyConverter();
    private final List<String> currencyCodes = new ArrayList<>();
    private final List<String[]> currencyDetails = new ArrayList<>();

    private final JPanel historyPanel = new JPanel();

    public CurrencyConverterApp() {
        loadCurrencies();
    }

    public void launch() {
        JFrame frame = new JFrame("Currency Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(675, 350);
        frame.setLayout(new BorderLayout());

        // Configure the history panel
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        JScrollPane historyScrollPane = new JScrollPane(historyPanel);
        historyScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        historyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        historyScrollPane.setPreferredSize(new Dimension(150, 0));
        historyScrollPane.setBorder(BorderFactory.createTitledBorder("Conversion History"));

        JPanel inputPanel = new JPanel(new GridBagLayout());
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


        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(fromCurrencyLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(fromCurrencyDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(toCurrencyLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(toCurrencyDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        convertButton.setPreferredSize(new Dimension(150, 25));
        inputPanel.add(convertButton, gbc);

        gbc.gridy = 4;
        inputPanel.add(resultLabel, gbc);

        frame.add(historyScrollPane, BorderLayout.WEST);
        frame.add(inputPanel, BorderLayout.CENTER);

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

                    addHistoryCard(command);

                    double convertedAmount = command.getConvertedAmount();
                    resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, fromCurrency, convertedAmount, toCurrency));
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid amount. Please enter a valid number.");
                } catch (Exception ex) {
                    resultLabel.setText("Conversion failed: " + ex.getMessage());
                }
            });
        });

        frame.setVisible(true);
    }

    private void addHistoryCard(ExchangeCommand command) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        JPanel historyCard = new JPanel(new BorderLayout());
        historyCard.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel cardLabel = new JLabel(
                "<html>" +
                        "<div style='padding: 5px; border: 1px solid #ccc; border-radius: 5px; background: #f4f4f4;'>" +
                        "<b>Time:</b> " + timestamp + "<br>" +
                        "<b>Amount:</b> " + command.getAmount() + " " + command.getSourceCurrency() + "<br>" +
                        "<b>Converted:</b> " + command.getConvertedAmount() + " " + command.getTargetCurrency() +
                        "</div>" +
                        "</html>");

        historyCard.add(cardLabel, BorderLayout.CENTER);
        historyCard.setPreferredSize(new Dimension(180, 80));

        historyPanel.add(historyCard, 0); // Add at the top

        historyPanel.revalidate();
        historyPanel.repaint();
    }

    private void loadCurrencies() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/currencies.tsv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                currencyCodes.add(parts[0]);
                currencyDetails.add(parts);
            }
        } catch (Exception e) {
            System.err.println("Failed to load currencies: " + e.getMessage());
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyConverterApp().launch());
    }
}
