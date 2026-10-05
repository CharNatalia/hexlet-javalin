package org.example.hexlet;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.controller.SessionsController;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.util.NamedRoutes;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class HelloWorld {
    public static void main(String[] args) {
        // Создаем приложение
        var app = Javalin.create(config -> {
            config.fileRenderer(new JavalinJte(createTemplateEngine()));

            config.routes.before(ctx -> {
                System.out.println(LocalDateTime.now());


                System.out.println("PATH: " + ctx.path());
                System.out.println("METHOD: " + ctx.method());
                System.out.println("CURRENT USER: " + ctx.sessionAttribute("currentUser"));

                if (ctx.path().equals(NamedRoutes.buildSessionsPath())
                        || ctx.path().equals(NamedRoutes.sessionsPath())) {
                    return;
                }

                if (ctx.sessionAttribute("currentUser") == null) {
                    ctx.redirect(NamedRoutes.buildSessionsPath());
                    ctx.skipRemainingHandlers();
                }
            });

            // Отображение формы логина
            config.routes.get(NamedRoutes.buildSessionsPath(), SessionsController::build);
            // Процесс логина
            config.routes.post(NamedRoutes.sessionsPath(), SessionsController::create);
            // Процесс выхода из аккаунта
            config.routes.delete(NamedRoutes.sessionsPath(), SessionsController::destroy);


            config.routes.get(NamedRoutes.coursesPath(), CoursesController::index);

            config.routes.get(NamedRoutes.buildCoursePath(), CoursesController::build);

            config.routes.get(NamedRoutes.coursePath("{id}"), CoursesController::show);

            config.routes.post(NamedRoutes.coursesPath(), CoursesController::create);


            config.routes.get(NamedRoutes.buildUserPath(), UsersController::build);

            config.routes.post(NamedRoutes.usersPath(), UsersController::create);

            config.routes.get(NamedRoutes.usersPath(), UsersController::index);

            config.routes.get(NamedRoutes.rootPath(), ctx -> ctx.render("index.jte"));

//            config.routes.get(NamedRoutes.rootPath(), ctx -> {
//                var visited = Boolean.valueOf(ctx.cookie("visited"));
//                var page = new MainPage(visited);
//                ctx.render("index.jte", Map.of("page", page));
//                ctx.cookie("visited", String.valueOf(true));
//            });


        });

        app.start(7070); // Стартуем веб-сервер
    }

    private static TemplateEngine createTemplateEngine() {
        var codeResolver = new DirectoryCodeResolver(Path.of("src/main/jte"));
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }
}