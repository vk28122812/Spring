package io.datajek.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(EcommerceApplication.class, args);
    }

}