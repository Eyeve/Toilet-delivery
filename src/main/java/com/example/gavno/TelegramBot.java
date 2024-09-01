package com.example.gavno;

import com.example.gavno.Command.*;
import com.example.gavno.Config.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final OrderRepository orderRepository;
    private final Map<String, Command> commandMap;
    private final Command unknownCommand;

    public TelegramBot(BotConfig config, UserRepository userRepository, OrderRepository orderRepository) {
        this.config = config;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;

        commandMap = Map.of(
                "/start", new StartCommand(),
                "/help", new HelpCommand(),
                "/me", new MeCommand(userRepository, orderRepository),
                "/buy", new BuyCommand(userRepository, orderRepository)
        );
        unknownCommand = new UnknownCommand();
    }

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
        Command command = commandMap.getOrDefault(checkIsUserExists(update, userId) ? update.getMessage().getText() : "/start", unknownCommand);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(command.execute(userId));

        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkIsUserExists(Update update, Long userId) {
        if (!userRepository.existsById(userId)) {
            var from = update.getMessage().getFrom();
            User user = new User(
                    userId,
                    from.getFirstName(),
                    from.getLastName(),
                    from.getUserName(),
                    Boolean.TRUE.equals(from.getIsPremium()),
                    Boolean.TRUE.equals(from.getIsBot()),
                    Boolean.TRUE.equals(from.getCanJoinGroups())
            );
            userRepository.save(user);
            return false;
        }
        return true;
    }
}
