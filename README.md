# Money Calculator

A Java-based currency conversion application with a graphical user interface (GUI). The application allows users to convert between currencies and keeps a history of conversions.

---

## Features

- **Currency Conversion**: Convert between different currencies using real-time exchange rates.
- **Conversion History**: View a scrollable history of previous conversions.
- **Dynamic Dropdowns**: Select currencies from a dropdown menu populated with real-time data.

---

## What This Project Does

The Money Calculator project is a currency conversion tool built using Java and Swing for the GUI. It fetches real-time currency exchange rates and enables users to:

1. Enter an amount and select source and target currencies to convert.
2. See the converted amount displayed immediately.
3. Maintain a scrollable history of all conversions for reference.

The application is divided into components, each responsible for specific tasks:

- **Commands**: Handles the execution and tracking of conversion actions.
- **GUI**: The graphical interface, including panels for user input and history display.
- **Service Layer**: Includes classes for fetching exchange rates and performing conversions.
- **Configuration**: Manages application settings such as default currencies.

---

## Project Structure

```
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com.moneycalculator
│   │   │   │   ├── commands
│   │   │   │   │   ├── Command.java
│   │   │   │   │   ├── CommandHistory.java
│   │   │   │   │   ├── ExchangeCommand.java
│   │   │   │   ├── gui
│   │   │   │   │   ├── InputPanel.java
│   │   │   │   │   ├── HistoryPanel.java
│   │   │   │   │   ├── MainApp.java
│   │   │   │   ├── service
│   │   │   │   │   ├── CurrencyRateService.java
│   │   │   │   │   ├── MoneyConverter.java
│   │   │   │   ├── config
│   │   │   │   │   ├── ApplicationConfig.java
│   │   │   │   ├── impl
│   │   │   │   │   ├── CurrencyRateFetcher.java
│   │   │   │   ├── model
│   │   │   │   │   ├── Rate.java
│   ├── resources
│       ├── currencies.tsv
```

---

## Usage

1. Launch the application.
2. Enter the amount to convert in the input field.
3. Choose the source and target currencies from the dropdown menus.
4. Click "Convert" to see the conversion result.
5. Check the scrollable history panel on the left for previous conversions.

---

## Acknowledgments

- Exchange rate fetching powered by `CurrencyRateService`.
- GUI components designed using Java Swing.

---

Enjoy using the Money Calculator for accurate and quick currency conversions!
