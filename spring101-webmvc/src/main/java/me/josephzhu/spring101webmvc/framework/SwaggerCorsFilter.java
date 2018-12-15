package me.josephzhu.spring101webmvc.framework;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author zhuye
 * @date 2018/12/14
 */
public class SwaggerCorsFilter extends CorsFilter {

    public SwaggerCorsFilter() {
        super(configurationSource());
    }

    private static UrlBasedCorsConfigurationSource configurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("api_key");
        config.addAllowedHeader("Authorization");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/v2/api-docs", config);
        return source;
    }
}