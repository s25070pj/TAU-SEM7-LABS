package com.example.tausem7.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void createdVehicleShouldBeInstanceOfBike() {
        Vehicle bike = new Bike();

        assertThat(bike).isInstanceOf(Bike.class);
    }

    @Test
    void createdVehicleShouldBeInstanceOfCar() {
        Vehicle car = new Car();

        assertThat(car).isInstanceOf(Car.class);
    }

    @Test
    void listOfVehiclesShouldBeEmpty() {
        List<Vehicle> vehicles = List.of();

        assertThat(vehicles).isEmpty();
    }

    @Test
    void nameOfCreatedVehicleShouldBeNull() {
        Vehicle car = new Car();

        assertThat(car.getName()).isNull();
    }

    @Test
    void nameOfCreatedVehicleShouldNotBeNull() {
        Vehicle car = new Car();
        car.setName("Volvo");

        assertThat(car.getName()).isNotNull();
    }

    @Test
    void shouldChangeCarNameFromNullToVolvo() {
        Vehicle car = new Car();
        assertThat(car.getName()).isNull();

        car.setName("Volvo");

        assertThat(car.getName()).isEqualTo("Volvo");
    }

    @Test
    void listOfVehiclesShouldContainsTwoElementsAfterAddBikeAndCar() {
        Vehicle bike = new Bike();
        Vehicle car = new Car();
        List<Vehicle> vehicles = new ArrayList<>();
        assertThat(vehicles).isEmpty();

        vehicles.add(bike);
        vehicles.add(car);

        assertThat(vehicles).size().isEqualTo(2);
    }

    @Test
    void bikeShouldBeFirstElementInVehiclesList() {
        Vehicle bike = new Bike();
        Vehicle car = new Car();
        List<Vehicle> vehicles = new ArrayList<>();
        assertThat(vehicles).isEmpty();

        vehicles.add(bike);
        vehicles.add(car);

        assertThat(vehicles.get(0)).isEqualTo(bike);
    }

    @Test
    void listOfVehiclesShouldBeEmptyAfterClear() {
        Vehicle bike = new Bike();
        Vehicle car = new Car();
        List<Vehicle> vehicles = new ArrayList<>(List.of(bike, car));

        vehicles.clear();

        assertThat(vehicles).isEmpty();
    }

    @Test
    void shouldThrowIndexOutOfBoundsException() {
        Vehicle bike = new Bike();
        Vehicle car = new Car();
        List<Vehicle> vehicles = new ArrayList<>();

        vehicles.add(bike);
        vehicles.add(car);

        assertThrows(IndexOutOfBoundsException.class, () -> vehicles.get(2));
    }
}