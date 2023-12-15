package com.betfair.logistics.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import static com.betfair.logistics.dao.entity.OrderStatus.allowedTransitions;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public boolean changeOrderStatus(OrderStatus newStatus) {

        if (allowedTransitions.get(this.orderStatus).contains(newStatus)) {
            this.orderStatus = newStatus;
            return true;
        }
        return false;

    }
}
