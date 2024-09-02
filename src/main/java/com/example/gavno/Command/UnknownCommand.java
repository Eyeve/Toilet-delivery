package com.example.gavno.Command;

import com.example.gavno.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class UnknownCommand extends Command {

    private static final String TEXT = "Неизвестная команда, попробуйте /help";

    public UnknownCommand(TelegramBot bot) {
        super(bot);
    }
    @Override
    public void execute(Update update) {
        SendMessage message = generateMessage(update.getMessage().getChatId(), TEXT);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
