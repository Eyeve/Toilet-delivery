package com.example.gavno.Command;

import com.example.gavno.TelegramBot;
import com.example.gavno.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

public class MeCommand extends Command {

    private static final String PATTERN = "Ваш профиль [%s]";

    public MeCommand(TelegramBot bot) {
        super(bot);
    }
    @Override
    public void execute(Update update) {
        Optional<User> from = bot.getUserRepository().findById(update.getMessage().getFrom().getId());
        SendMessage message;
        User user;

        if (from.isEmpty()) {
            throw new RuntimeException("Unknown user"); // TODO: redo
        }
        user = from.get();
        message = generateMessage(update.getMessage().getChatId(), String.format(PATTERN, user.getUsername()));
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
