package com.arenabooking.arena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArenaApplication.class, args);
	}
  SpringApplication app = new SpringApplication(YourApplication.class);
        Environment environment = app.run(args).getEnvironment();
        String port = environment.getProperty("PORT");
        if (port != null) {
            System.setProperty("server.port", port);
        }
    }
}
