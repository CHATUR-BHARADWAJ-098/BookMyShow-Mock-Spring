package com.example.firstspringproj;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = "com.example.firstspringproj.models")
public class FirstSpringProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstSpringProjApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean<WebServlet> h2ConsoleServletRegistration() {
        ServletRegistrationBean<WebServlet> bean = new ServletRegistrationBean<>(new WebServlet(), "/h2-console/*");
        bean.setLoadOnStartup(1);
        return bean;
    }

}
