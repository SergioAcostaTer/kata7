package com.moneycalculator.gui;

import javax.swing.*;
import java.awt.*;

public class HistoryPanel {
    private final JPanel panel;
    private final JScrollPane scrollPane;

    public HistoryPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(150, 0));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Conversion History"));
    }

    public JScrollPane getPanel() {
        return scrollPane;
    }

    public void addHistoryCard(String historyCardHtml) {
        JLabel cardLabel = new JLabel(historyCardHtml);
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.add(cardLabel, BorderLayout.CENTER);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        cardPanel.setPreferredSize(new Dimension(180, 80));

        panel.add(cardPanel, 0);
        panel.revalidate();
        panel.repaint();
    }
}
