package com.momentus.foundation.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "modules")
@Data                       // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor           // Lombok: generates a no-args constructor
@AllArgsConstructor
public class Module {

    @Id
    String moduleCode;


    @Column
    String moduleName;

    @Column(length = 100)
    String description;



}


