package com.ku.controller;

import com.ku.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author ku
 * @date 2020/12/13
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * {
     *   "phone": "13826812363",
     *   "verifyCode": "123456"
     * }
     * @param params
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> verifyUser(@RequestBody Map<String, Object> params) {
        System.out.println("手机号参数：" + params.get("phone"));
        System.out.println("验证码参数：" + params.get("verifyCode"));
        Map<String, Object> resultMap = userService.verifyUser(params);
        return ResponseEntity.ok(resultMap);
    }

}
