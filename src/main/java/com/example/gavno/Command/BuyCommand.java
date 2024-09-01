package com.example.gavno.Command;

import com.example.gavno.OrderRepository;
import com.example.gavno.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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
    public String execute(Long userId) {
        return null;
    }
}
