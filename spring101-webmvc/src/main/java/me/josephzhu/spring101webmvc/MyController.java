package me.josephzhu.spring101webmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MyController {

    @GetMapping("shop")
    public ModelAndView shop() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shop");
        modelAndView.addObject("items",
                IntStream.range(1, 5)
                        .mapToObj(i -> new MyItem("item" + i, i * 100))
                        .collect(Collectors.toList()));
        return modelAndView;
    }

}
