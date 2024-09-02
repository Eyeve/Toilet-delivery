package com.example.gavno.Command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class HelpCommand implements Command {
    /*
        Shows possible commands
     */
    private static final String TEXT = String.format("%s\n%s\n%s",
            "/buy - сделать заказ",
            "/me - показать информацию о заказах и балансе",
            "/help - вывести данное сообщение");
    @Override
    public void execute(Long userId, SendMessage message) {
        message.setText(TEXT);
    }
}
