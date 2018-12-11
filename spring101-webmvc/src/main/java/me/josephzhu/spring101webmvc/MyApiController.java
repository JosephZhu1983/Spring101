package me.josephzhu.spring101webmvc;

import lombok.extern.slf4j.Slf4j;
import me.josephzhu.spring101webmvc.framework.ActionFilter;
import me.josephzhu.spring101webmvc.framework.ApiController;
import me.josephzhu.spring101webmvc.framework.ApiException;
import me.josephzhu.spring101webmvc.framework.ApiVersion;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@ApiController
@Slf4j
@ApiVersion("1")
public class MyApiController {

    @ActionFilter(value = TestActionFilter1.class, order = 100)
    @ActionFilter(TestActionFilter2.class)
    @RequestMapping(value = "items", method = RequestMethod.GET)
    public List<MyItem> getItems() {
        List<MyItem> myItems = new ArrayList<>();
        myItems.add(new MyItem("aa", 10));
        myItems.add(new MyItem("cc", 20));
        return myItems;
    }

    @ActionFilter(TestActionFilter2.class)
    @RequestMapping(value = "item/{id}", method = RequestMethod.GET)
    public MyItem getItem(@PathVariable("id") String id) {
        Integer i = null;
        try {
            i = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
        }
        if (i == null)
            throw new ApiException("1001", "商品ID只能是数字");
        if (i < 1)
            throw new IllegalArgumentException("不合法的商品ID");

        return new MyItem("item" + id, 10);
    }

    @ActionFilter(TestActionFilter2.class)
    @RequestMapping(value = "item/{id}", method = RequestMethod.GET)
    @ApiVersion({"2","3"})
    public MyItem getItemv2(@PathVariable("id") String id) {
        return new MyItem("item" + id, 20);
    }
}
