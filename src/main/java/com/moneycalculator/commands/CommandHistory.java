package com.moneycalculator.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory implements Command {

    private static final List<ExchangeCommand> history = new ArrayList<>();

    public static void addCommand(ExchangeCommand command) {
        history.add(command);
    }

    public static List<ExchangeCommand> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public void execute() {
        System.out.println("Command History:");
        for (ExchangeCommand command : history) {
            System.out.println("Converted " + command.getAmount() + " " + command.getSourceCurrency() + " to "
                    + command.getTargetCurrency() + " = " + command.getConvertedAmount());
        }
    }
}
