package com.ku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ku
 * @date 2020/12/25
 */
@SpringBootApplication
public class UploadApplication {

    public static void main(String[] args){
        SpringApplication.run(UploadApplication.class,args);
        System.out.println("upload...");
    }

}
