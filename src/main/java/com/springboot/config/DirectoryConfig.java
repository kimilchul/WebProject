package com.springboot.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DirectoryConfig implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {

        File directory = new File("/images");

        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
