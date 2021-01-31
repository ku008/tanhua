package com.ku;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ku
 * @date 2020/12/13
 */
@SpringBootApplication
@MapperScan("com.ku.dao")
public class SsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
        System.out.println("sso...");
    }

}
