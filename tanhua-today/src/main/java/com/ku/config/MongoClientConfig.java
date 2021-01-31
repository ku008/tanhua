package com.ku.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ku
 * @date 2020/12/22
 */
//@Configuration
public class MongoClientConfig {

    @Autowired
    private MongoProperties mongoProperties;

    @Bean
    public MongoClient mongo(){
        return new MongoClient(new MongoClientURI(mongoProperties.determineUri(), MongoClientOptions.builder()));
    }

}
