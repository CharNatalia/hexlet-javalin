package org.example.hexlet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public final class Course {
    private static Long idNumber = 0L;

    private Long id;
    @ToString.Include
    private String name;
    private String description;

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = getIdNumber();
    }

    public static Long getIdNumber() {
        idNumber++;
        return idNumber;
    }
}

