package me.josephzhu.spring101ops;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("api")
public class MyController {

    @Autowired
    MeterRegistry meterRegistry;

    @GetMapping("items")
    public List<MyItem> items(@RequestParam(value = "count",defaultValue = "10") int count){
        try {
            Thread.sleep(count);
        } catch (InterruptedException e) { }
        meterRegistry.counter("mycounter").increment(count);
        meterRegistry.gauge("currentCounter", count);
        meterRegistry.timer("mytimer").record(Duration.ofSeconds(count));
        return IntStream.rangeClosed(1,count).mapToObj(i->new MyItem("name" + i,i)).collect(Collectors.toList());
    }
}
