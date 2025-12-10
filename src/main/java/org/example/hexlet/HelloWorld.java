package org.example.hexlet;

import io.javalin.Javalin;


public class HelloWorld {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        app.get("users/{id}/post/{postId}", ctx -> {
            var usersId = ctx.pathParam("id");
            var postId =  ctx.pathParam("postId");
            ctx.result("Users ID: " + usersId + " Post ID: " + postId);
        });

        app.start(7070);
    }
}
