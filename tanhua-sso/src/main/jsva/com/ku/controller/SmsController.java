package com.ku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ku
 * @date 2020/12/13
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/sms")
public class SmsController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate = null;

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Map<String, Object>> sendPhoneCode(@PathVariable String phone) {
        System.out.println("获取到的手机号：" + phone);
        String code = "123456";
        System.out.println("验证码：" + code);
        Map<String, Object> resultMap = new HashMap<>();
        if (phone == null) {
            resultMap.put("code", 205);
            resultMap.put("msg", "获取手机号失败...");
            return ResponseEntity.ok(resultMap);
        }
        if (stringRedisTemplate.opsForValue().get(phone) != null) {
            resultMap.put("code", 205);
            resultMap.put("msg", "验证码还未过期...");
            return ResponseEntity.ok(resultMap);
        }
        stringRedisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
        resultMap.put("code", 200);
        resultMap.put("msg", "获取验证码成功...");
        resultMap.put("data", code);
        return ResponseEntity.ok(resultMap);
    }

}
