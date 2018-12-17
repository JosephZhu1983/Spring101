package me.josephzhu.spring101webmvc;

import io.swagger.annotations.ApiOperation;
import me.josephzhu.spring101webmvc.framework.ApiController;
import me.josephzhu.spring101webmvc.framework.exception.ApiException;
import me.josephzhu.spring101webmvc.framework.version.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@ApiController
@ApiVersion("")
public class ExceptionController {
    @GetMapping("item/{id}")
    @ApiOperation("测试异常")
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
    @ApiOperation("测试POJO参数校验异常")
    public MyItem setItem(MyItem item) {
        return item;
    }
}
