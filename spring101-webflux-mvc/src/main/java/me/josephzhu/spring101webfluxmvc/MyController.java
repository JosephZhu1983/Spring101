package me.josephzhu.spring101webfluxmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class MyController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MyRepository myRepository;

    @GetMapping("/data")
    public List<MyData> getData(@RequestParam(value = "size", defaultValue = "10") int size,@RequestParam(value = "length", defaultValue = "100") int length) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        String payload = IntStream.rangeClosed(1,length).mapToObj(i->"a").collect(Collectors.joining());
        return IntStream.rangeClosed(1, size)
                .mapToObj(i->new MyData(UUID.randomUUID().toString(), payload, System.currentTimeMillis()))
                .collect(Collectors.toList());
    }

    @GetMapping("/dbData")
    public List<MyData> getDbData() {
        return myRepository.findAll();
    }

    @GetMapping("/saveData")
    public List<MyData> saveData(@RequestParam(value = "size", defaultValue = "10") int size,@RequestParam(value = "length", defaultValue = "100") int length){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://127.0.0.1:8080/data")
                .queryParam("size", size)
                .queryParam("length", length);
        ResponseEntity<List<MyData>> responseEntity =
                restTemplate.exchange(builder.toUriString(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<MyData>>() {});
        return responseEntity.getBody().stream().map(myRepository::save).collect(Collectors.toList());
    }
}
