package com.soen342.sniffnjack.Controller;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.soen342.sniffnjack.Entity.User;
import com.soen342.sniffnjack.Exceptions.UserNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.List;

@Component
public class UserServer {

    @Autowired
    private UserController userController;

    private final Gson gson = new Gson(); // Gson instance for JSON serialization/deserialization

    public void startServer() throws IOException {
        System.out.println("Starting UserServer...");
        HttpServer server = HttpServer.create(new InetSocketAddress(2211), 0);
        server.createContext("/users/all", this::handleGetAllUsers);
        server.createContext("/users/get", this::handleFindUserByEmail);
        server.createContext("/users/delete", this::handleDeleteUser);
        server.createContext("/users/create", this::handleCreateUser);
        server.createContext("/users/update", this::handleUpdateUser);
        server.start();
        System.out.println("Server started on port 2211");
    }

    private void handleGetAllUsers(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            List<User> users = (List<User>) userController.getAllUsers();
            String response = gson.toJson(users); // Use Gson to convert list of users to JSON

            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleFindUserByEmail(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String query = exchange.getRequestURI().getQuery();
            String email = query.split("=")[1];
            try {
                User user = userController.findUserByEmail(email);
                String response = gson.toJson(user); // Convert user to JSON
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (UserNotFoundException e) {
                String response = "User not found";
                exchange.sendResponseHeaders(404, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleDeleteUser(HttpExchange exchange) throws IOException {
        if ("DELETE".equals(exchange.getRequestMethod())) {
            String query = exchange.getRequestURI().getQuery();
            String email = query.split("=")[1];
            try {
                userController.deleteUser(email);
                String response = "Deleted user: " + email;
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (UserNotFoundException e) {
                String response = "User not found";
                exchange.sendResponseHeaders(404, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleCreateUser(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStream is = exchange.getRequestBody();
            User user = gson.fromJson(new InputStreamReader(is), User.class); // Deserialize JSON to User

            userController.createUser(user);
            String response = "User created: " + user.getEmail();
            exchange.sendResponseHeaders(201, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleUpdateUser(HttpExchange exchange) throws IOException {
        if ("PATCH".equals(exchange.getRequestMethod())) {
            InputStream is = exchange.getRequestBody();
            User user = gson.fromJson(new InputStreamReader(is), User.class); // Deserialize JSON to User

            try {
                userController.updateUser(user);
                String response = "User updated: " + user.getEmail();
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (UserNotFoundException e) {
                String response = "User not found";
                exchange.sendResponseHeaders(404, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }
}
