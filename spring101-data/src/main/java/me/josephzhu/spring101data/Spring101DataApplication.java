package me.josephzhu.spring101data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@SpringBootApplication
@EnableJdbcAuditing
@Configuration
public class Spring101DataApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring101DataApplication.class, args);
	}
}
