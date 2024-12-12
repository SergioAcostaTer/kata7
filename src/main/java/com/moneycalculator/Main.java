package com.moneycalculator;

import com.moneycalculator.gui.CurrencyConverterApp;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyConverterApp().launch());
    }
}
