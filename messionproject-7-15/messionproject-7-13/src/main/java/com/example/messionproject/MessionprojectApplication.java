package com.example.messionproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.example.messionproject.mapper")
public class MessionprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessionprojectApplication.class, args);
    }

}
