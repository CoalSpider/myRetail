package com.ben.myretail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.ben.myretail.repository")
public class MyretailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyretailApplication.class, args);
    }

   
}
