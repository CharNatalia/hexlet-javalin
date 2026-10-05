package org.example.hexlet.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;
import org.example.hexlet.dto.courses.BuildCoursePage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.repository.CourseRepository;
import org.example.hexlet.util.NamedRoutes;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class CoursesController {
    public static void index(Context ctx) {
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
        var page = new CoursesPage(listForRender, header, term);
        ctx.render("courses/index.jte", Map.of("page", page));
    }

    public static void show(Context ctx) {
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
    }

    public static void build(Context ctx) {
        var page = new BuildCoursePage();
        ctx.render("courses/build.jte", Map.of("page", page));
    }

    public static void create(Context ctx) {
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
    }

    public static void edit(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var course =
                CourseRepository.find(id)
                        .orElseThrow(
                                () ->
                                        new NotFoundResponse(
                                                "Entity with id = " + id + " not found"));
        var page = new CoursePage(course);
        ctx.render("courses/edit.jte", Map.of("page", page));
    }

    public static void update(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();

        var name = ctx.formParam("name");
        var description = ctx.formParam("description");

        var course =
                CourseRepository.find(id)
                        .orElseThrow(
                                () ->
                                        new NotFoundResponse(
                                                "Entity with id = " + id + " not found"));
        course.setName(name);
        course.setDescription(description);
        CourseRepository.save(course);
        ctx.redirect(NamedRoutes.coursesPath());
    }

    public static void destroy(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        CourseRepository.delete(id);
        ctx.redirect(NamedRoutes.coursesPath());
    }
}
