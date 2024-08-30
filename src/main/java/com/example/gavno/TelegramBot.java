package com.example.gavno;

import com.example.gavno.Command.*;
import com.example.gavno.Config.BotConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final Map<String, Command> commandMap = Map.of(
            "/start", new StartCommand(),
            "/help", new HelpCommand(),
            "/me", new MeCommand(),
            "/buy", new BuyCommand(userRepository, orderRepository)
    );

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        Long userId = update.getMessage().getFrom().getId();
        Command command = commandMap.get(update.getMessage().getText());
        SendMessage message = new SendMessage();

        message.setChatId(chatId);
        if (command != null) {
            message.setText(command.execute(userId));
        } else {
            message.setText("Неизвестная команда, попробуйте /help");
        }

        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
