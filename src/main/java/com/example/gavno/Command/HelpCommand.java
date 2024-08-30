package com.example.gavno.Command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpCommand implements Command {
    /*
        Shows possible commands
     */
    @Override
    public SendMessage execute(final Update update) {
        return null;
    }
}
