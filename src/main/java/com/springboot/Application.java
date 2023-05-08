package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@PropertySource(value = {"application.yml","application-datasource.yml","application-oauth.yml"},factory = YamlLoadFactory.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
        System.out.println(System.getProperty("java.version"));
    }
}
