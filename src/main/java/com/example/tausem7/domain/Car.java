package com.example.tausem7.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@DiscriminatorValue("Car")
public class Car extends Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;



}
