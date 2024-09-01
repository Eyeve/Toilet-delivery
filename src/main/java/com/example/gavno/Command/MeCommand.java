package com.example.gavno.Command;

import com.example.gavno.OrderRepository;
import com.example.gavno.User;
import com.example.gavno.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@AllArgsConstructor
public class MeCommand implements Command {
    /*
        Payment method details
        Orders
     */
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final OrderRepository orderRepository;
    @Override
    public String execute(Long userId) {
        Optional<User> from = userRepository.findById(userId);
        if (from.isEmpty()) {
            throw new RuntimeException("Unknown user");
        }
        User user = from.get();
        return String.format("Ник: %s\nИмя: %s\nФамилия: %s", user.getUserName(), user.getFirstName(), user.getLastName());
    }
}
