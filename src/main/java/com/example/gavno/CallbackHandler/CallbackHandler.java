package com.example.gavno.CallbackHandler;

import com.example.gavno.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class CallbackHandler {

    protected final TelegramBot bot;

    public CallbackHandler(TelegramBot bot) {
        this.bot = bot;
    }

    public abstract void process(Update update);
}
