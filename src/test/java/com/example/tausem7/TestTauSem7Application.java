package com.example.tausem7;

import org.springframework.boot.SpringApplication;

public class TestTauSem7Application {

    public static void main(String[] args) {
        SpringApplication.from(TauSem7Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
