package com.ku.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ku
 * @date 2020/12/19
 */
public class TokenTest {

    public static void main(String[] args){
        Map<String,Object> params = new HashMap<>();
        params.put("uid",1);
        params.put("phone","ku");
        String token = JwtUtils.generateToken(params, "ku688", 30);
        System.out.println("token:"+token);
        Map<String, Object> result = JwtUtils.getInfoFromToken(token, "ku688");
        System.out.println("结果："+result);
    }

}
