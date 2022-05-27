package com.hetongxue.configuration;

import com.hetongxue.lang.Const;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: SpringWeb相关配置
 * @ClassNmae: SpringWebConfiguration
 * @Author: 何同学
 * @DateTime: 2022-05-23 19:33
 **/
@Configuration
@EnableWebMvc
public class SpringWebConfiguration implements WebMvcConfigurer {

    private final String[] classpathResourceLocations = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/", "classpath:/META-INF/resources/webjars/"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(classpathResourceLocations);
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// 添加映射路径
                .allowedHeaders("*")// 放行哪些原始请求头部信息
                .exposedHeaders("*")// 暴露哪些头部信息
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE", "TRACE", "HEAD", "PATCH")// 放行哪些请求方式
                .allowCredentials(true)// 是否发送 Cookie
                .maxAge(3600)// 最大时间
                .exposedHeaders(Const.AUTHORIZATION_KEY)
                .allowedOriginPatterns("*");
//                .allowedOriginPatterns("http://127.0.0.1:8080")
//                .allowedOriginPatterns("http://127.0.0.1:8082");
    }

}
