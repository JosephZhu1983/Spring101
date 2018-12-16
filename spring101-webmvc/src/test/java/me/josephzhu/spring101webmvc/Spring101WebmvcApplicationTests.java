package me.josephzhu.spring101webmvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Spring101WebmvcApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testApiVersionController() throws Exception {
        mvc.perform(get("/v1/version/hello")).andExpect(content().json("{'data':'hello1'}"));
        mvc.perform(get("/version/hello")).andExpect(content().json("{'data':'hello2'}"));
        mvc.perform(get("/v2/version/hello")).andExpect(content().json("{'data':'hello3'}"));
        mvc.perform(get("/v3/version/hello")).andExpect(content().json("{'data':'hello3'}"));

        mvc.perform(get("/v1/version/hello/zhuye")).andExpect(content().json("{'data':'hello1zhuye'}"));
        mvc.perform(get("/version/hello/zhuye")).andExpect(content().json("{'data':'hello2zhuye'}"));
        mvc.perform(get("/v2/version/hello/zhuye")).andExpect(content().json("{'data':'hello3zhuye'}"));
        mvc.perform(get("/v3/version/hello/zhuye")).andExpect(content().json("{'data':'hello3zhuye'}"));

    }
}
