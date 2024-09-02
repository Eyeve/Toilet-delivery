package com.example.gavno.Command;

import com.example.gavno.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Command {

    protected final TelegramBot bot;

    public Command(TelegramBot bot) {
        this.bot = bot;
    }
    public abstract void execute(Update update);
    protected SendMessage generateMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        return message;
    }
}
