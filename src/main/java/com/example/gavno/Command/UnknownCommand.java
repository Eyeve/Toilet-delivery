package com.example.gavno.Command;

public class UnknownCommand implements Command {
    @Override
    public String execute(Long userId) {
        return "Неизвестная команда, попробуйте /help";
    }
}
