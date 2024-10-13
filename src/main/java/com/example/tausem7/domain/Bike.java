package com.example.tausem7.domain;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@Builder
@Entity
public class Bike extends Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

}
