package com.kimu.dicamsi.newdichamsi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NewdichamsiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewdichamsiApplication.class, args);
    }

}
