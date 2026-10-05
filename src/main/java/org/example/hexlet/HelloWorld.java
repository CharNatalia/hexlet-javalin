package org.example.hexlet;

import io.javalin.Javalin;

import java.time.LocalDateTime;
import java.util.List;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.validation.ValidationException;
import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.dto.courses.BuildCoursePage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.CourseRepository;
import org.example.hexlet.repository.UserRepository;
import org.example.hexlet.util.NamedRoutes;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class HelloWorld {
    public static void main(String[] args) {
// Создаем приложение
        var app = Javalin.create(config -> {
            config.fileRenderer(new JavalinJte(createTemplateEngine()));

            config.routes.before(ctx -> {
                System.out.println(LocalDateTime.now());
            });
            config.routes.get(NamedRoutes.coursesPath(), CoursesController::index);

            config.routes.get(NamedRoutes.buildCoursePath(), CoursesController::build);

            config.routes.get(NamedRoutes.coursePath("{id}"), CoursesController::show);

            config.routes.post(NamedRoutes.coursesPath(), CoursesController::create);


            config.routes.get(NamedRoutes.buildUserPath(), UsersController::build);

            config.routes.post(NamedRoutes.usersPath(), UsersController::create);

            config.routes.get(NamedRoutes.usersPath(), UsersController::index);

            config.routes.get(NamedRoutes.rootPath(), ctx -> ctx.render("index.jte"));


        });

        app.start(7070); // Стартуем веб-сервер
    }

    private static TemplateEngine createTemplateEngine() {
        var codeResolver = new DirectoryCodeResolver(Path.of("src/main/jte"));
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }
}