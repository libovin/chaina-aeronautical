package com.hiekn.china.aeronautical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ChinaAeronauticalApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChinaAeronauticalApplication.class, args);
    }
}
