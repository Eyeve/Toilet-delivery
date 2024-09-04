package com.example.gavno.CallbackHandler;

import com.example.gavno.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class AddToiletPaperHandler extends CallbackHandler {

    public AddToiletPaperHandler(TelegramBot bot) {
        super(bot);
    }
    @Override
    public void process(Update update) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessage.setText("Changed correctly!");

        try {
            bot.execute(editMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
