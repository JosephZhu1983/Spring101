package me.josephzhu.spring101webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Autowired
    private MyHandler myHandler;

    @Bean
    public RouterFunction<ServerResponse> config() {
        return route(GET("/data"), myHandler::getData)
                .andRoute(GET("/dbData"), myHandler::getDbData)
                .andRoute(GET("/saveData"), myHandler::saveData);
    }
}
