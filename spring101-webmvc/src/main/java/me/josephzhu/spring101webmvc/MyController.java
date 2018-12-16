package me.josephzhu.spring101webmvc;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.josephzhu.spring101webmvc.framework.exception.ApiException;
import me.josephzhu.spring101webmvc.framework.filter.ApiFilter;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@ApiFilter(LoginCheck.class)
@RequestMapping("rest")
public class MyController {

    @ApiFilter(value = TestApiFilter1.class, order = 100)
    @ApiFilter(TestApiFilter2.class)
    @GetMapping("item")
    @ApiOperation("filter无效")
    public MyItem getItem1() {
        return new MyItem("aa", 10);
    }

    @GetMapping("item/{id}")
    @ApiOperation("异常无法自动包装")
    public MyItem getItem(@PathVariable("id") String id) {
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

    @PostMapping("item")
    @ApiOperation("需要手动@RequestBody")
    public MyItem setItem(MyItem item) {
        return item;
    }

    @PostMapping("item2")
    @ApiOperation("需要手动@RequestBody")
    public MyItem setItem2(@RequestBody MyItem item) {
        return item;
    }

}
