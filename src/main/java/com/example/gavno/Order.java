package com.example.gavno;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_order", indexes = @Index(name = "idx_buyer_id", columnList = "buyerId"))
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Long buyerId;
    private Integer sum;

    private Integer toiletPaper;
    private Integer instantNoodles;

}
