package me.josephzhu.spring101webmvc;

import io.swagger.annotations.ApiOperation;
import me.josephzhu.spring101webmvc.framework.ApiController;
import me.josephzhu.spring101webmvc.framework.version.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@ApiController
@ApiVersion("v1")
@RequestMapping("ver")
public class VersionController {
    @GetMapping("hello")
    @ApiOperation("使用定义在Controller的版本号")
    public String hello1() {
        return "hello1";
    }

    @GetMapping("hello")
    @ApiVersion("")
    @ApiOperation("定义在方法的版本号可以覆盖")
    public String hello2() {
        return "hello2";
    }

    @GetMapping("hello")
    @ApiVersion({"v2", "v3"})
    @ApiOperation("支持多个版本号")
    public String hello3() {
        return "hello3";
    }

    @GetMapping("hello/{name}")
    public String hello1(@PathVariable("name") String name) {
        return "hello1" + name;
    }

    @GetMapping("hello/{name}")
    @ApiVersion("")
    public String hello2(@PathVariable("name") String name) {
        return "hello2" + name;
    }

    @GetMapping("hello/{name}")
    @ApiVersion({"v2", "v3"})
    public String hello3(@PathVariable("name") String name) {
        return "hello3" + name;
    }
}
