package com.ota.api.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Config.class)
public class NoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteApplication.class, args);
    }

}
