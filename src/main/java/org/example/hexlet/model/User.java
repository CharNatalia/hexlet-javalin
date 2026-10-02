package org.example.hexlet.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class User {
    private Long id;

    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
