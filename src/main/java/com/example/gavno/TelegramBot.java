package com.example.gavno;

import com.example.gavno.CallbackHandler.AddToiletPaperHandler;
import com.example.gavno.CallbackHandler.CallbackHandler;
import com.example.gavno.Command.*;
import com.example.gavno.Config.BotConfig;
import com.example.gavno.Config.CallbackConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Getter
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final OrderRepository orderRepository;
    private final BotLogger logger;

    private final Command startCommand;
    private final Command helpCommand;
    private final Command meCommand;
    private final Command unknownCommand;
    private final Command buyCommand;
    private final Map<String, Command> commandMap;
    private final Map<String, CallbackHandler> handlerMap;

    public TelegramBot(BotConfig config, UserRepository userRepository, OrderRepository orderRepository) {
        this.config = config;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.logger = new BotLogger(System.out);

        this.startCommand = new StartCommand(this);
        this.helpCommand = new HelpCommand(this);
        this.meCommand = new MeCommand(this);
        this.unknownCommand = new UnknownCommand(this);
        this.buyCommand = new BuyCommand(this);

        this.commandMap = Map.of(
                "/start", startCommand,
                "/help", helpCommand,
                "/me", meCommand,
                "/buy", buyCommand
        );
        this.handlerMap = Map.of(
                CallbackConfig.TOILET_PAPER_ADD, new AddToiletPaperHandler(this)
//                CallbackConfig.INSTANT_NOODLES_CALLBACK, null,
//                CallbackConfig.NEXT_CALLBACK, null,
//                CallbackConfig.PREVIOUS_CALLBACK, null,
//                CallbackConfig.BACK_CALLBACK, null
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
        } else {
            processMessage(update);
        }
    }
    private void processMessage(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        Command command = generateCommand(update, userId);

        command.execute(update);
    }
    private Command generateCommand(Update update, Long userId) {
        if (checkIsUserExists(update, userId)) {
           return commandMap.getOrDefault(update.getMessage().getText(), unknownCommand);
        }
        return startCommand;
    }
    private void processCallbackData(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        CallbackHandler handler = handlerMap.getOrDefault(callbackQuery.getData(), null);

        if (handler == null) {
            logger.error(String.format("Unknown callback data: %s", callbackQuery.getData()));
        } else {
            handler.process(update);
        }
    }
    private boolean checkIsUserExists(Update update, Long userId) { // TODO: rename
        if (userRepository.existsById(userId)) {
            return true;
        }

        var from = update.getMessage().getFrom();
        User user = new User(
                userId,
                from.getFirstName(),
                from.getLastName(),
                from.getUserName(),
                Boolean.TRUE.equals(from.getIsPremium()),
                Boolean.TRUE.equals(from.getIsBot()),
                Boolean.TRUE.equals(from.getCanJoinGroups()),
                0,
                0
        );
        userRepository.save(user);
        return false;
    }
}
