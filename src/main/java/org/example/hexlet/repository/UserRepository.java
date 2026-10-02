package org.example.hexlet.repository;

import org.example.hexlet.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static final List<User> ENTITIES = new ArrayList<>();

    public static void save(User user) {
        user.setId((long) ENTITIES.size() + 1);
        ENTITIES.add(user);
    }

    public static Optional<User> find(Long id) {
        return ENTITIES.stream().filter(entity -> entity.getId().equals(id)).findAny();
    }

    public static void delete(Long id) {
        ENTITIES.removeIf(entity -> entity.getId().equals(id));
    }

    public static List<User> getEntities() {
        return ENTITIES;
    }
}
