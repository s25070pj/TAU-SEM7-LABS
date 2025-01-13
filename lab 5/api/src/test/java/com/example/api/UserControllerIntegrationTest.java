package com.example.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ApiApplication.class)
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void exercisePath() throws Exception {
        // 1. When I  go to /users I can see empty users array
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        // 2. When I go to /users/1 I can see 404 not found

        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // 3. When I post to /users with user "John", "john@john.pl" then user "John", "john@john.pl" with id 1 is returned
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "John",
                                    "email": "john@john.pl"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                                {
                                    "id": 1,
                                    "name": "John",
                                    "email": "john@john.pl"
                                }
                        """));

        // 4.1 When I go to /users/1 I can see the user "John" returned with status 200
        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                {
                                    "id": 1,
                                    "name": "John",
                                    "email": "john@john.pl"
                                }
                        """));

        // 4. When I go to /users I can see users array with one record
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].name").isString());

        //5. When I try to create a user with incomplete data, I get a 400 Bad Request
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "John"
                                }
                                """))
                .andExpect(status().isBadRequest());

        //5.1 When I try to create a user not valid email, I get a 400 Bad Request
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "John",
                                    "email": "johnjohnpl"
                                }
                                """))
                .andExpect(status().isBadRequest());

        // 6. When I update the user with id 1 to "Jane", "jane@jane.com", updated data is returned
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Jane",
                                    "email": "jane@jane.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                {
                                    "id": 1,
                                    "name": "Jane",
                                    "email": "jane@jane.com"
                                }
                        """));

        // 7. When I go to /users/1 after the update, I see the updated user data
        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                {
                                    "id": 1,
                                    "name": "Jane",
                                    "email": "jane@jane.com"
                                }
                        """));

        // 8. When I try to update a non-existing user, I get 404 Not Found
        mockMvc.perform(put("/users/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 100,
                                    "name": "Jane",
                                    "email": "jane@jane.com"
                                }
                                """))
                .andExpect(status().isNotFound());

        // 9. When I delete user with id 1, I get status 204 no content
        mockMvc.perform(delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // 10. When I go to /users/1 after deletion, I get 404 Not Found
        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}
