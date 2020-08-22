package com.web.crawlerjd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrawlerJdApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlerJdApplication.class, args);
    }

}
