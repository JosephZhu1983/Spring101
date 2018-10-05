package me.josephzhu.spring101webfluxmvc;

import com.mongodb.MongoClientOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Configuration
public class Spring101WebfluxMvcApplication {

   @Bean
   MongoClientOptions mongoClientOptions(){
       return MongoClientOptions.builder().connectionsPerHost(1000).build();
   }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Spring101WebfluxMvcApplication.class, args);
    }
}
