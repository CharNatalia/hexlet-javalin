package org.example.hexlet.repository;

import org.example.hexlet.model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository {
    private static final List<Course> ENTITIES = new ArrayList<>();

    public static void save(Course course) {
        course.setId((long) ENTITIES.size() + 1);
        ENTITIES.add(course);
    }

    public static Optional<Course> find(Long id) {
        return ENTITIES.stream().filter(entity -> entity.getId().equals(id)).findAny();
    }

    public static void delete(Long id) {
        ENTITIES.removeIf(entity -> entity.getId().equals(id));
    }

    public static List<Course> getEntities() {
        return ENTITIES;
    }
}
