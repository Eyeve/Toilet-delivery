package com.example.gavno;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private String userName;
    private String address;

    private boolean isPremium;
    private boolean isBot;
    private boolean canJoinGroups;
}
