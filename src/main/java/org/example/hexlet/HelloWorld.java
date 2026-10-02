package org.example.hexlet;

import io.javalin.Javalin;

import java.util.List;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.rendering.template.JavalinJte;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.courses.UsersPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.CourseRepository;
import org.example.hexlet.repository.UserRepository;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class HelloWorld {
    public static void main(String[] args) {
// Создаем приложение
        var app = Javalin.create(config -> {
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
            config.routes.get("/courses", ctx -> {
                List<Course> courses = CourseRepository.getEntities();
                var term = ctx.queryParam("term");
                List<Course> listForRender;
                if (term != null) {
                    listForRender =
                            courses.stream()
                                    .filter(
                                            course ->
                                                    course.getName()
                                                            .toLowerCase(Locale.ROOT)
                                                            .startsWith(
                                                                    term.toLowerCase(Locale.ROOT))
                                    || (course.getDescription()
                                    .toLowerCase(Locale.ROOT)
                                    .contains(
                                            term.toLowerCase(Locale.ROOT))))
                                    .toList();
                } else {
                    listForRender = courses;
                }
                var header = "Курсы по программированию";
                var page = new CoursesPage(listForRender, header,term);
                ctx.render("courses/index.jte", Map.of("page", page));
            });

            config.routes.get("/courses/build", ctx ->
            {
                ctx.render("courses/build.jte");
            });

            config.routes.get("/courses/{id}", ctx -> {
                List<Course> courses = CourseRepository.getEntities();
                var id = ctx.pathParamAsClass("id", Long.class).get();
                Course result = courses.stream()
                        .filter(course -> Objects.equals(course.getId(), id))
                        .findFirst()
                        .orElse(null);

                if (result == null) {
                    ctx.status(404).result("Not found");
                    return;
                }

                var page = new CoursePage(result);

                ctx.render("courses/show.jte", Map.of("page", page));

            });

            config.routes.post("/courses", ctx -> {
                var name = ctx.formParam("name").trim();
                var description = ctx.formParam("description").trim();

                var course = new Course(name, description);
                CourseRepository.save(course);
                ctx.redirect("/courses");
            });


            config.routes.get("/users/build", ctx -> {
                ctx.render("users/build.jte");
            });

            config.routes.post("/users", ctx -> {
                var name = ctx.formParam("name").trim();
                var email = ctx.formParam("email").trim().toLowerCase();
                var password = ctx.formParam("password");
                var passwordConfirmation = ctx.formParam("passwordConfirmation");

                var user = new User(name, email, password);
                UserRepository.save(user);
                ctx.redirect("/users");
            });

            config.routes.get("/users", ctx -> {
                List<User> users = UserRepository.getEntities();
                var page = new UsersPage(users);
                ctx.render("users/index.jte", Map.of("page", page));
            });

            config.routes.get("/", ctx -> ctx.render("index.jte"));

        });
        app.start(7070); // Стартуем веб-сервер
    }

    private static TemplateEngine createTemplateEngine() {
        var codeResolver = new DirectoryCodeResolver(Path.of("src/main/jte"));
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }
}