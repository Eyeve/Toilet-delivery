package com.example.gavno.CallbackHandler;

import com.example.gavno.TelegramBot;

public abstract class CallbackHandler {

    private final TelegramBot bot;

    public CallbackHandler(TelegramBot bot) {
        this.bot = bot;
    }

    public abstract void process();
}
