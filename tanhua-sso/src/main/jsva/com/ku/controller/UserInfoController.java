package com.ku.controller;

import com.ku.pojo.UserInfo;
import com.ku.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author ku
 * @date 2020/12/19
 */
@RestController
@CrossOrigin
@RequestMapping("/userinfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * {
     *   "phone": "13826812363",
     *   "name": "ku",
     *   "sex": "男",
     *   "age": 3,
     *   "email": "123@qq",
     *   "avatar": "http://localhost:28888"
     * }
     * @param userInfo
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserInfo userInfo) {
        System.out.println("用户详细信息参数："+userInfo);
        Map<String, Object> resultMap = userInfoService.registerUser(userInfo);
        return ResponseEntity.ok(resultMap);
    }

}
