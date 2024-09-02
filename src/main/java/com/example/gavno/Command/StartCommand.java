package com.example.gavno.Command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class StartCommand implements Command {
    /*
        Tells about the service
     */
    private static final String TEXT = "Приветсвуем вас! Вы у нас впервые!";
    @Override
    public void execute(Long userId, SendMessage message) {
        message.setText(TEXT);
    }
}
