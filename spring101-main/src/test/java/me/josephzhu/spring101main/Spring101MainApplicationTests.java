package me.josephzhu.spring101main;

import me.josephzhu.spring101customstarter.AbstractMyService;
import me.josephzhu.spring101customstarter.MyAutoConfiguration;
import me.josephzhu.spring101customstarter.MyService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


public class Spring101MainApplicationTests {

    private ApplicationContextRunner applicationContextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(MyAutoConfiguration.class));

    @Test
    public void testService() {
        applicationContextRunner
                .withPropertyValues("spring101.age=35")
                .withPropertyValues("spring101.name=zhuye")
                .run(context -> {
                    assertThat(context).hasSingleBean(AbstractMyService.class);
                    assertThat(context.getBean(AbstractMyService.class).hello()).containsSequence("zhuye:35");
                    System.out.println(context.getBean(MyService.class).hello());
                });
    }

    @Test
    public void testConditionalOnProperty() {
        applicationContextRunner
                .run(context -> {
                    assertThat(context).hasSingleBean(AbstractMyService.class);
                    assertThat(context.getBean(AbstractMyService.class).hello()).containsSequence("V1 Hello");
                    System.out.println(context.getBean(AbstractMyService.class).hello());
                });
        applicationContextRunner
                .withPropertyValues("spring101.version=v1")
                .run(context -> {
                    assertThat(context).hasSingleBean(AbstractMyService.class);
                    assertThat(context.getBean(AbstractMyService.class).hello()).containsSequence("V1 Hello");
                    System.out.println(context.getBean(AbstractMyService.class).hello());
                });
        applicationContextRunner
                .withPropertyValues("spring101.version=v2")
                .run(context -> {
                    assertThat(context).hasSingleBean(AbstractMyService.class);
                    assertThat(context.getBean(AbstractMyService.class).hello()).containsSequence("V2 Hello");
                    System.out.println(context.getBean(AbstractMyService.class).hello());
                });

    }

    @Test
    public void testConditionalOnMissingBean() {
        applicationContextRunner
                .withUserConfiguration(MyServiceConfig.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(MyService.class);
                    assertThat(context.getBean(MyService.class).hello()).containsSequence("V1 Hi");
                    System.out.println(context.getBean(MyService.class).hello());
                });
    }

}

@Configuration
class MyServiceConfig {
    @Bean
    MyService getService() {
        return new MyService("Hi");
    }
}
