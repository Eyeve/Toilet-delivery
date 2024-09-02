package com.example.gavno.Command;

import com.example.gavno.Config.CallbackConfig;
import com.example.gavno.OrderRepository;
import com.example.gavno.User;
import com.example.gavno.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class BuyCommand implements Command {
    /*
        Displays a menu with a selection of products
     */
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final OrderRepository orderRepository;
    @Override
    public void execute(Long userId, SendMessage message) {
        Optional<User> from = userRepository.findById(userId);
        if (from.isEmpty()) {
            throw new RuntimeException("Unknown user");
        }

        // TODO: implementation

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        message.setText("Заменить на изображение с описанием товаров");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<InlineKeyboardButton> row4 = new ArrayList<>();

        addButton(row1, "Рулон туалетной бумаги", CallbackConfig.TOILET_PAPER_CALLBACK);
        addButton(row2, "Доширак", CallbackConfig.INSTANT_NOODLES_CALLBACK);
        addButton(row3, "«", CallbackConfig.PREVIOUS_CALLBACK);
        addButton(row3, "»", CallbackConfig.NEXT_CALLBACK);
        addButton(row4, "Назад в профиль", CallbackConfig.BACK_CALLBACK);

        // Add all rows to the keyboard
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);

        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
    }

    private static void addButton(List<InlineKeyboardButton> row, String description, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(description);
        button.setCallbackData(callbackData);
        row.add(button);
    }


}
