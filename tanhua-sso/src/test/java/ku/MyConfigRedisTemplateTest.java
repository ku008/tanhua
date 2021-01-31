package com.ku;

import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyConfigRedisTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        redisTemplate.opsForValue().set("name", "ku");
        System.out.println(redisTemplate.opsForValue().get("name"));
        Map<String, Object> map = new HashMap<>();
        for (int i=0; i<10; i++){
            User user = new User();
            user.setId(i);
            user.setName(String.format("测试%d", i));
            user.setAge(i+10);
            map.put(String.valueOf(i),user);
        }
        redisTemplate.opsForHash().putAll("测试", map);
        BoundHashOperations hashOps = redisTemplate.boundHashOps("测试");
        Map map1 = hashOps.entries();
        System.out.println(map1);
    }

    @Data
    static class User implements Serializable {
    private int id;
    private String name;
    private long age;
    }
}
