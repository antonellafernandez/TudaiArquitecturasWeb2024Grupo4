package com.example.microservicio_cuenta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroservicioCuentaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioCuentaApplication.class, args);
    }

}
