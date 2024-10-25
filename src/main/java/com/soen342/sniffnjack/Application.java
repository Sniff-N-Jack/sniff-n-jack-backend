package com.soen342.sniffnjack;

import com.soen342.sniffnjack.Repository.Impl.CustomMongoRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.soen342.sniffnjack.Repository", repositoryBaseClass = CustomMongoRepositoryImpl.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
