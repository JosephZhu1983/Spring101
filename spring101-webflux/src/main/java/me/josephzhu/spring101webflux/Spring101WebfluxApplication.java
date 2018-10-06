package me.josephzhu.spring101webflux;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class Spring101WebfluxApplication {

    @Bean
    MongoClient mongoClient(){
        return MongoClients.create(mongoClientSettings());
    }

    @Bean
    MongoClientSettings mongoClientSettings(){
        return MongoClientSettings.builder()
                .clusterSettings(ClusterSettings.builder().applyConnectionString(new ConnectionString("mongodb://localhost")).build())
                .connectionPoolSettings(ConnectionPoolSettings.builder().minSize(50).maxSize(1000).maxWaitQueueSize(1000000).build())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Spring101WebfluxApplication.class, args);
    }
}
