package com.example.gavno.Command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {
    /*
        Tells about the service
     */
    @Override
    public String execute(Long userId) {
        return "Start command!";
    }
}
