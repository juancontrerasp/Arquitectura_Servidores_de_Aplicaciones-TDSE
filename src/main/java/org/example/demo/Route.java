package org.example.demo;

@FunctionalInterface
public interface Route {
    String handle(Request req, Response res);
}
