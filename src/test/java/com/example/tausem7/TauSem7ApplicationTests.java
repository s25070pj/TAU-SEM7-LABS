package com.example.tausem7;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TauSem7ApplicationTests {

    @Test
    void contextLoads() {
    }

}
