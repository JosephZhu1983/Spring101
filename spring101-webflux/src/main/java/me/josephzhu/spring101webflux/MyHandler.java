package me.josephzhu.spring101webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class MyHandler {
    @Autowired
    private MyReactiveRepository myReactiveRepository;

    public Mono<ServerResponse> getData(ServerRequest serverRequest) {
        int size = Integer.parseInt(serverRequest.queryParam("size").orElse("10"));
        int length = Integer.parseInt(serverRequest.queryParam("length").orElse("100"));

        String payload = IntStream.rangeClosed(1,length).mapToObj(i->"a").collect(Collectors.joining());
        Flux<MyData> data = Flux.fromStream(IntStream.rangeClosed(1, size)
                .mapToObj(i->new MyData(UUID.randomUUID().toString(), payload, System.currentTimeMillis()))).delaySequence(Duration.ofMillis(100));

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data, MyData.class);
    }

    public Mono<ServerResponse> getDbData(ServerRequest serverRequest) {
        Flux<MyData> data = myReactiveRepository.findAll();
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data, MyData.class);
    }

    public Mono<ServerResponse> saveData(ServerRequest serverRequest) {
        int size = Integer.parseInt(serverRequest.queryParam("size").orElse("10"));
        int length = Integer.parseInt(serverRequest.queryParam("length").orElse("100"));

        Flux<MyData> data = WebClient.create().get()
                .uri(builder -> builder
                        .scheme("http")
                        .host("localhost")
                        .port(8080)
                        .path("data")
                        .queryParam("size", size)
                        .queryParam("length", length)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(MyData.class)
                .flatMap(myReactiveRepository::save);

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data, MyData.class);
    }

}
