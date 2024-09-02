package com.example.gavno.Command;

import com.example.gavno.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class HelpCommand extends Command {

    private static final String TEXT =
            "/buy - сделать заказ\n" +
            "/me - показать информацию о заказах и балансе\n" +
            "/help - вывести данное сообщение";

    public HelpCommand(TelegramBot bot) {
        super(bot);
    }
    @Override
    public void execute(Update update) {
        SendMessage message = generateMessage(update.getMessage().getChatId(), TEXT);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);  // TODO: redo
        }
    }
}
