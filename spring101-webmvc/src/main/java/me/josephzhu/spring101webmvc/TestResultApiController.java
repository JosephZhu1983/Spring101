package me.josephzhu.spring101webmvc;

import me.josephzhu.spring101webmvc.framework.ApiController;
import me.josephzhu.spring101webmvc.framework.result.NoApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@ApiController
@RequestMapping("result")
public class TestResultApiController {

    @GetMapping("test1")
    public boolean test1() {
        return false;
    }

    @GetMapping("test2")
    public float test2() {
        return .2f;
    }

    @GetMapping("test3")
    public MyItem test3() {
        return new MyItem("aa", 10);
    }

    @GetMapping("test4")
    public void test4() {
    }

    @GetMapping("test5")
    @NoApiResult
    public MyItem test5() {
        return new MyItem("aa", 10);
    }

}
