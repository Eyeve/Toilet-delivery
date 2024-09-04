package com.example.gavno.Command;

import com.example.gavno.Config.CallbackConfig;
import com.example.gavno.TelegramBot;
import com.example.gavno.User;
import com.example.gavno.UserRepository;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class BuyCommand extends Command {

    private static final InlineKeyboardMarkup markup = generateKeyboardMarkup();

    public BuyCommand(TelegramBot bot) {
        super(bot);
    }
    @Override
    public void execute(Update update) {
        Optional<User> optional = bot.getUserRepository().findById(update.getMessage().getFrom().getId());
        SendMessage message;

        if (optional.isPresent()) {
            message = generateMessage(update.getMessage().getChatId(), generateText(optional.get()));
            message.setReplyMarkup(markup);
            try {
                bot.execute(message);
            } catch (TelegramApiException e) {
                bot.getLogger().error("Can't execute buy command");
            }
        } else {
            bot.getLogger().critical(String.format("Unknown user found! Id is %d", update.getMessage().getFrom().getId()));
        }
    }
    public String generateText(final User user) {
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append(user.getFirstName());
        strBuilder.append(' ');
        strBuilder.append(user.getLastName());
        strBuilder.append("\nВ корзине:\n");
        addItem(strBuilder, "Рулон туалетной бумаги", user.getToiletPaper());
        addItem(strBuilder, "Доширак", user.getInstantNoodles());
        return strBuilder.toString();
    }

    private static void addItem(StringBuilder strBuilder, final String name, final int count) {
        if (count > 0) {
            strBuilder.append(String.format(" %s - x%d", name, count));
        }
    }

    private static InlineKeyboardMarkup generateKeyboardMarkup() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        addChoice(keyboard, "Рулон туалетной бумаги", CallbackConfig.TOILET_PAPER);
        addChoice(keyboard, "Доширак", CallbackConfig.INSTANT_NOODLES);
        addKeyboardButton(keyboard, "Оформить заказ", CallbackConfig.MAKE_ORDER);
        addKeyboardButton(keyboard, "Назад в профиль", CallbackConfig.BACK);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }
    private static void addChoice(List<List<InlineKeyboardButton>> keyboard, String description, String callbackData) {
        List<InlineKeyboardButton> mainRow = new ArrayList<>();
        List<InlineKeyboardButton> additionalRow = new ArrayList<>();

        addRowButton(mainRow, description, callbackData);
        addRowButton(additionalRow, "-", CallbackConfig.deletePostfix(callbackData));
        addRowButton(additionalRow, "+", CallbackConfig.addPostfix(callbackData));
        keyboard.add(mainRow);
        keyboard.add(additionalRow);
    }
    private static void addKeyboardButton(List<List<InlineKeyboardButton>> keyboard, String description, String callbackData) {
        List<InlineKeyboardButton> row = new ArrayList<>();

        addRowButton(row, description, callbackData);
        keyboard.add(row);
    }
    private static void addRowButton(List<InlineKeyboardButton> row, String description, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();

        button.setText(description);
        button.setCallbackData(callbackData);
        row.add(button);
    }
}
