package com.truxxkart.promotionservice_v1.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private Long userId; // The user placing the order

//    @Column(nullable = false)
//    private LocalDateTime orderDate;

//    @Column(nullable = false)
//    private String status; // PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Column(nullable = false)
    private double totalOrderAmount;
    
    @Column(nullable = false)
  private String paymentMode; 

    @Column(nullable = true)
    private String paymentId; // ID of the payment transaction (if integrated)

  
}
