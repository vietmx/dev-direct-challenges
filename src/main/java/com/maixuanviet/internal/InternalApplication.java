package com.maixuanviet.internal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author VietMX
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.maixuanviet"})
@EnableCaching
public class InternalApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternalApplication.class, args);
    }

}
