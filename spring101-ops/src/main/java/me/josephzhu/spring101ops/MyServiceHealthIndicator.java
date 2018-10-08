package me.josephzhu.spring101ops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MyServiceHealthIndicator implements HealthIndicator {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Health health() {
        try {
            ResponseEntity<List<MyItem>> responseEntity =
                    restTemplate.exchange("http://localhost:8080/api/it1ems",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<MyItem>>() {});
            if (responseEntity.getStatusCode().is2xxSuccessful())
                return Health.up().build();
            else
                return Health.down().status(responseEntity.getStatusCode().toString()).build();

        } catch (Exception ex) {
            return Health.down(ex).build();
        }
    }
}
