package com.moneycalculator.gui;

import com.moneycalculator.commands.ExchangeCommand;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryPanel extends JPanel {

    public HistoryPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 240, 240));
        setPreferredSize(new Dimension(200, 0));
        setBorder(BorderFactory.createTitledBorder("Conversion History"));
    }

    public void addHistoryCard(ExchangeCommand command) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        JPanel historyCard = new JPanel(new BorderLayout());
        historyCard.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        historyCard.setBackground(new Color(220, 220, 220)); // Slightly darker gray for cards

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

        add(historyCard, 0);
        revalidate();
        repaint();
    }
}
