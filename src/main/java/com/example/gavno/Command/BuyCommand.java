package com.example.gavno.Command;

import com.example.gavno.Config.CallbackConfig;
import com.example.gavno.TelegramBot;
import com.example.gavno.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuyCommand extends Command {

    public BuyCommand(TelegramBot bot) {
        super(bot);
    }
    @Override
    public void execute(Update update) {
        // TODO: extract
        Optional<User> from = bot.getUserRepository().findById(update.getMessage().getFrom().getId());
        if (from.isEmpty()) {
            throw new RuntimeException("Unknown user");
        }

        SendMessage message = generateMessage(update.getMessage().getChatId(), "Заменить на изображение с описанием товаров");
        InlineKeyboardMarkup keyboardMarkup = generateKeyboardMarkup();
        User user = from.get();

        message.setReplyMarkup(keyboardMarkup);
        try {
            Message response = bot.execute(message);
            user.setCatalogId(response.getMessageId());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e); // TODO: redo
        }
    }

    private static InlineKeyboardMarkup generateKeyboardMarkup() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<InlineKeyboardButton> row4 = new ArrayList<>();

        addButton(row1, "Рулон туалетной бумаги", CallbackConfig.TOILET_PAPER_CALLBACK);
        addButton(row2, "Доширак", CallbackConfig.INSTANT_NOODLES_CALLBACK);
        addButton(row3, "«", CallbackConfig.PREVIOUS_CALLBACK);
        addButton(row3, "»", CallbackConfig.NEXT_CALLBACK);
        addButton(row4, "Назад в профиль", CallbackConfig.BACK_CALLBACK);

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);

        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    private static void addButton(List<InlineKeyboardButton> row, String description, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(description);
        button.setCallbackData(callbackData);
        row.add(button);
    }


}
