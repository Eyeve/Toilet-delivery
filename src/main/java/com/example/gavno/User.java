package com.example.gavno;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private String userName;

    private boolean isPremium;
    private boolean isBot;
    private boolean canJoinGroups;
}
