package me.josephzhu.spring101aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MyController {

    @Autowired
    private DbMapper dbMapper;

    @ResponseBody
    @GetMapping("/data")
    public List<MyBean> getPersonList(){
        return dbMapper.getPersonList();
    }
}
