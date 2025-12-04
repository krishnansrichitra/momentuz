package com.momentus.foundation.common.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data                       // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor           // Lombok: generates a no-args constructor
@AllArgsConstructor          // Lombok: generates an all-args constructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address1;

    private String address2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false, length = 10)
    private String zipcode;

    @Column(length = 15)
    private String phoneNumber;
}
