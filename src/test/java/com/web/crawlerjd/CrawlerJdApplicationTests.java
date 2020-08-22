package com.web.crawlerjd;

import com.web.crawlerjd.task.ItemTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrawlerJdApplicationTests {

    @Autowired
    private ItemTask itemTask;

    @Test
    void contextLoads() {
        try {
            itemTask.itemTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
