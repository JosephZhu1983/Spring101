package me.josephzhu.spring101webmvc;

import lombok.extern.slf4j.Slf4j;
import me.josephzhu.spring101webmvc.framework.exception.ApiException;
import me.josephzhu.spring101webmvc.framework.filter.ApiFilter;
import me.josephzhu.spring101webmvc.framework.version.ApiVersion;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@ApiVersion("v1")
@ApiFilter(LoginCheck.class)
@RequestMapping("rest")
public class MyController {

    @GetMapping("hello")
    public String hello(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @PostMapping("item")
    public MyItem setItem(@RequestBody MyItem myItem) {
        return myItem;
    }

    @ApiFilter(value = TestApiFilter1.class, order = 100)
    @ApiFilter(TestApiFilter2.class)
    @GetMapping("items")
    public List<MyItem> getItems() throws InterruptedException {
        Thread.sleep(1000);
        List<MyItem> myItems = new ArrayList<>();
        myItems.add(new MyItem("aa", 10));
        myItems.add(new MyItem("cc", 20));
        return myItems;
    }

    @ApiFilter(TestApiFilter2.class)
    @GetMapping("item/{id}")
    @ApiVersion({"v2", "v3"})
    public MyItem getItemv2(@PathVariable("id") String id) {
        Integer i = null;
        try {
            i = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
        }
        if (i == null)
            throw new ApiException("1001", "商品ID只能是数字");
        if (i < 1)
            throw new RuntimeException("不合法的商品ID");

        return new MyItem("item" + id, 10);
    }
}
