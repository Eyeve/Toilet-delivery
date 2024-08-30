package com.example.gavno;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProductList {
    @Id
    @GeneratedValue
    private Long id;
    private Long orderId;
    private int name;
    private int count;
}
