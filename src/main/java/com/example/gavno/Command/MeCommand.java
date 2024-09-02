package com.example.gavno.Command;

import com.example.gavno.OrderRepository;
import com.example.gavno.User;
import com.example.gavno.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;

@AllArgsConstructor
public class MeCommand implements Command {
    /*
        Payment method details
        Orders
     */
    private static final String PATTERN = "Ваш профиль [%s]";

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
        User user = from.get();
        message.setText(String.format(PATTERN, user.getUsername()));
    }
}
