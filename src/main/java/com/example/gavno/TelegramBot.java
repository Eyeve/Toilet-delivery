package com.example.gavno;

import com.example.gavno.Command.*;
import com.example.gavno.Config.BotConfig;
import com.example.gavno.Config.CallbackConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
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
    private final Command unknownCommand;
    private final Command startCommand;
    private final Command meCommand;
    private final Map<String, Command> commandMap;

    public TelegramBot(BotConfig config, UserRepository userRepository, OrderRepository orderRepository) {
        this.config = config;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;

        startCommand = new StartCommand();
        unknownCommand = new UnknownCommand();
        meCommand = new MeCommand(userRepository, orderRepository);

        commandMap = Map.of(
                "/start", startCommand,
                "/help", new HelpCommand(),
                "/me", meCommand,
                "/buy", new BuyCommand(userRepository, orderRepository)
        );
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
        if (update.hasCallbackQuery()) {
            processCallbackData(update);
            return;
        }

        Long chatId = update.getMessage().getChatId();
        Long userId = update.getMessage().getFrom().getId();
        Command command;

        if (checkIsUserExists(update, userId)) {
            command = commandMap.getOrDefault(update.getMessage().getText(), unknownCommand);
        } else {
            command = startCommand;
        }

        executeCommand(command, userId, chatId);
    }

    private void send(SendMessage message) {
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void processCallbackData(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();

        switch (callbackQuery.getData()) {
            case CallbackConfig.TOILET_PAPER_CALLBACK:
                // TODO: implementation
                break;
            case CallbackConfig.INSTANT_NOODLES_CALLBACK:
                // TODO: implementation
                break;
            case CallbackConfig.NEXT_CALLBACK:
                // TODO: implementation
                break;
            case CallbackConfig.PREVIOUS_CALLBACK:
                // TODO: implementation
                break;
            case CallbackConfig.BACK_CALLBACK:
                // TODO: implementation
                break;
            default:
                throw new AssertionError("Unknown callback data!");
        }
    }

    private void executeCommand(Command command, Long userId,  Long  chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        command.execute(userId, message);
        send(message);
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
