package com.example.gavno.Command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpCommand implements Command {
    /*
        Shows possible commands
     */
    @Override
    public String execute(Long userId) {
        return String.format("%s\n%s\n%s",
                "/buy - сделать заказ",
                "/me - показать информацию о заказах и балансе",
                "/help - вывести данное сообщение"
        );
    }
}
