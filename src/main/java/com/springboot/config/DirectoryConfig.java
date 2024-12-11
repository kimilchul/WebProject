package com.springboot.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DirectoryConfig implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String currentDir = System.getProperty("user.dir");
        File directory = new File(currentDir+"/images");

        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
