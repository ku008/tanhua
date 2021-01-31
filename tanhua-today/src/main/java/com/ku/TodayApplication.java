package com.ku;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ku
 * @date 2020/12/20
 */
/**
 * 去除mongodb自动配置
 */
//@SpringBootApplication(exclude = MongoAutoConfiguration.class)
//@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@SpringBootApplication
//@MapperScan("com.ku.dao")
//@EnableTransactionManagement
public class TodayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodayApplication.class, args);
        System.out.println("today...");
    }

}
