package com.example.gavno;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Long buyerId;
    private int sum;
    private ProductList productList;

}
