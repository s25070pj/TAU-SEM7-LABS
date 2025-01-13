package com.example.api.dto;

import java.util.Objects;

public class UserDto {

    private Long id;

    private String name;

    private String email;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserDto id(Long id) {
        this.id = id;
        return this;
    }

    public UserDto name(String name) {
        this.name = name;
        return this;
    }

    public UserDto email(String email) {
        this.email = email;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto userDto)) return false;

        if (!Objects.equals(id, userDto.id)) return false;
        if (!Objects.equals(name, userDto.name)) return false;
        return Objects.equals(email, userDto.email);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
