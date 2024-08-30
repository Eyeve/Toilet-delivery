package com.example.gavno;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Long buyerId;
    private Integer sum;
    private Integer countToiletPaper;

}
