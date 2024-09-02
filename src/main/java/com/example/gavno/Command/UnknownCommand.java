package com.example.gavno.Command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class UnknownCommand implements Command {

    private static final String TEXT = "Неизвестная команда, попробуйте /help";
    @Override
    public void execute(Long userId, SendMessage message) {
        message.setText(TEXT);
    }
}
