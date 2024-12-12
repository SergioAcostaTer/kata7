package com.moneycalculator.gui;

import com.moneycalculator.commands.ExchangeCommand;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryPanel extends JPanel {

    private final JPanel contentPanel;

    public HistoryPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setPreferredSize(new Dimension(170, 0));
        setBorder(BorderFactory.createTitledBorder("Conversion History"));

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
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

        contentPanel.add(historyCard, 0);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
