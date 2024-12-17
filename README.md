
# Money Calculator

Money Calculator is a Java-based currency conversion application with a graphical user interface (GUI). This project is similar to [Kata 6](https://github.com/SergioAcostaTer/kata6), but now the application is packaged as an executable `.exe` file that can be installed on Windows.

## Features
- **Currency Conversion**: Convert between different currencies using real-time exchange rates.
- **Conversion History**: View a scrollable history of previous conversions.
- **Dynamic Dropdowns**: Select currencies from a dropdown menu populated with real-time data.

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

## Installation
This project is packaged as an executable `.exe` file, similar to [Kata 6](https://github.com/SergioAcostaTer/kata6), but now you can install it directly on your Windows machine.

1. Build the project using Maven.
2. Run the generated `.exe` installer to install the application.

## Acknowledgments
- Exchange rate fetching powered by **CurrencyRateService**.
- GUI components designed using **Java Swing**.
