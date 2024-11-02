package com.soen342.sniffnjack;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.soen342.sniffnjack.Controller.UserServer;

import org.springframework.context.ApplicationContext;

@EnableJpaRepositories
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        UserServer server = context.getBean(UserServer.class);
        server.startServer();
    }
}

