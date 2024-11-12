package com.example.microservicio_monopatin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroservicioMonopatinApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioMonopatinApplication.class, args);
    }

}
