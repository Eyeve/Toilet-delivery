package com.example.gavno.Command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Command {
    void execute(Long userId, SendMessage message);
}
