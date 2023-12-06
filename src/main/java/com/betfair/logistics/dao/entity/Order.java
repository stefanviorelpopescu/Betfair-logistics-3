package com.betfair.logistics.dao.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long deliveryDate;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    Long lastUpdated;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    Destination destination;

}
