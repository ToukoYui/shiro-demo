package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example")
public class ShiroProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiroProjectApplication.class, args);
    }

}
