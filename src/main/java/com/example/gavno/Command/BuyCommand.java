package com.example.gavno.Command;

import com.example.gavno.OrderRepository;
import com.example.gavno.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
public class BuyCommand implements Command {
    /*
        Displays a menu with a selection of products
     */
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    @Override
    public String execute(Long userId) {
        return null;
    }
}
