package me.josephzhu.spring101webmvc.framework;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Configuration
@Profile("!PROD && !prod")
public class SwaggerConfig {
    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .apiInfo(new ApiInfo(configurableEnvironment.getProperty("spring.application.name"), "",
                        configurableEnvironment.getProperty("spring.application.version"), "",
                        new Contact(configurableEnvironment.getProperty("spring.application.contact"), "", ""),
                        "", "", new ArrayList<>()));
    }

    @Bean
    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    public CorsFilter swaggerCorsFilter() {
        return new SwaggerCorsFilter();
    }
 }
