package com.betfair.logistics.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String name;

    Integer distance;

    @JsonIgnore
    @OneToMany(mappedBy = "destination", cascade = CascadeType.REMOVE)
    List<Order> orders;
}
