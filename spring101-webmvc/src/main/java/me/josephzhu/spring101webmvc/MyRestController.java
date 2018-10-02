package me.josephzhu.spring101webmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@Slf4j
@RequestMapping("api")
public class MyRestController {

    @RequestMapping(value = "items", method = RequestMethod.GET)
    public List<MyItem> getItems(MyDevice device) {
        log.debug("Device : " + device);
        List<MyItem> myItems = new ArrayList<>();
        myItems.add(new MyItem("aa", 10));
        myItems.add(new MyItem("bb", 20));
        return myItems;
    }

    @RequestMapping(value = "item/{id}", method = RequestMethod.GET)
    public MyItem getItem(@PathVariable("id") String id) {
        Integer i = null;
        try {
            i = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
        }
        if (i == null || i < 1)
            throw new IllegalArgumentException("不合法的商品ID");
        return new MyItem("item" + id, 10);
    }

    @GetMapping("search")
    @Sign
    public List<MyItem> search(@RequestParam("type") ItemTypeEnum itemTypeEnum) {
        return IntStream.range(1, 5)
                .mapToObj(i -> new MyItem(itemTypeEnum.name() + i, i * 100))
                .collect(Collectors.toList());
    }

}
