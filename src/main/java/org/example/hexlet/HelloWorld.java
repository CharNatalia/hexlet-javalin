package org.example.hexlet;

import io.javalin.Javalin;

import java.util.List;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.validation.ValidationException;
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
            config.routes.get(NamedRoutes.coursesPath(), ctx -> {
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

            config.routes.get(NamedRoutes.buildCoursePath(), ctx ->
            {
                var page = new BuildCoursePage();
                ctx.render("courses/build.jte", Map.of("page", page));
            });

            config.routes.get(NamedRoutes.coursePath("{id}"), ctx -> {
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

            config.routes.post(NamedRoutes.coursesPath(), ctx -> {
                var name = ctx.formParam("name");
                var description = ctx.formParam("description");
                try {
                    var checkedName = ctx.formParamAsClass("name", String.class)
                            .check(value -> value.length() > 2, "Название курса слишком короткое")
                            .get();
                    var checkedDescription = ctx.formParamAsClass("description", String.class)
                            .check(value -> value.length() > 10, "Описание курса слишком короткое")
                            .get();
                    var course = new Course(checkedName, checkedDescription);
                    CourseRepository.save(course);
                    ctx.redirect(NamedRoutes.coursesPath());
                } catch (ValidationException e) {
                    var page = new BuildCoursePage(name, description, e.getErrors());
                    ctx.status(422);
                    ctx.render("courses/build.jte", Map.of("page", page));
                }


            });


            config.routes.get(NamedRoutes.buildUserPath(), ctx -> {
                var page = new BuildUserPage();
                ctx.render("users/build.jte", Map.of("page", page));
            });

            config.routes.post(NamedRoutes.usersPath(), ctx -> {
                var name = ctx.formParam("name");
                var email = ctx.formParam("email");

                try {
                    var passwordConfirmation = ctx.formParam("passwordConfirmation");
                    var password = ctx.formParamAsClass("password", String.class)
                            .check(value -> value.equals(passwordConfirmation), "Пароли не совпадают")
                            .check(value -> value.length() > 6, "У пароля недостаточная длина")
                            .get();
                    var user = new User(name, email, password);
                    UserRepository.save(user);
                    ctx.redirect(NamedRoutes.usersPath());
                } catch (ValidationException e) {
                    var page = new BuildUserPage(name, email, e.getErrors());
                    ctx.status(422);
                    ctx.render("users/build.jte", Map.of("page", page));
                }
            });

            config.routes.get(NamedRoutes.usersPath(), ctx -> {
                List<User> users = UserRepository.getEntities();
                var page = new UsersPage(users);
                ctx.render("users/index.jte", Map.of("page", page));
            });

            config.routes.get(NamedRoutes.rootPath(), ctx -> ctx.render("index.jte"));

        });
        app.start(7070); // Стартуем веб-сервер
    }

    private static TemplateEngine createTemplateEngine() {
        var codeResolver = new DirectoryCodeResolver(Path.of("src/main/jte"));
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }
}