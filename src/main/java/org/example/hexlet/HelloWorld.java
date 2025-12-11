package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import static io.javalin.rendering.template.TemplateUtil.model;

import org.example.hexlet.model.Course;
import org.example.hexlet.dto.courses.CoursesPage;

import java.util.ArrayList;
import java.util.List;

public class HelloWorld {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/courses", ctx -> {
            var courses = List.of(new Course("1", "njwenjde"), new Course("2", "njw2edewdwenjde"));
            var header = "Курсы по программированию";
            var page = new CoursesPage(courses, header);
            ctx.contentType("text/html; charset=utf-8");
            ctx.render("courses/index.jte", model("page", page));
        });

        app.start(7070);
    }
}