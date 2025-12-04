package com.momentus.foundation.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "applications")
@Data                       // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor           // Lombok: generates a no-args constructor
@AllArgsConstructor          // Lombok: generates an all-args constructor
public class Application {

    @Id
    private String appCode;

    @Column(nullable = false)
    private String appName;

    @Column(length = 100)
    private String appDescription;


}
