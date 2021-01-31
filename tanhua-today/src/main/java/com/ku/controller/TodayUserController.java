package com.ku.controller;

import com.ku.service.TodayUserService;
import com.ku.bean.TodayUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ku
 * @date 2020/12/21
 */
@RestController
@CrossOrigin
@RequestMapping("/today")
public class TodayUserController {

    @Autowired
    private TodayUserService todayUserService;

    @PostMapping("/insert")
    public ResponseEntity<Map<String,Object>> insertToday(@RequestBody TodayUser todayUser){
        System.out.println("添加佳人参数："+todayUser);
        Map<String,Object> resultMap = new HashMap<>();
        try {
            todayUserService.insert(todayUser);
            resultMap.put("code",200);
            resultMap.put("msg","添加佳人数据成功...");
        }catch(Exception e){
            resultMap.put("code",500);
            resultMap.put("msg","添加佳人数据失败...");
            resultMap.put("data",e.getMessage());
        }
        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> findAllToday(){
        System.out.println("获取所有佳人...");
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<TodayUser> list = todayUserService.findall();
            resultMap.put("code",200);
            resultMap.put("msg","获取所有佳人....");
            resultMap.put("data",list);
        }catch(Exception e){
            resultMap.put("code",500);
            resultMap.put("msg","获取所有佳人失败...");
            resultMap.put("data",e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("/findByMaxScore")
    public ResponseEntity<Map<String,Object>> findByMaxScore(){
        Map<String,Object> resultMap = todayUserService.findByMaxScore();
        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("/findByUserId/{userId}")
    public ResponseEntity<Map<String,Object>> findByUserId(@PathVariable String userId){
        Map<String,Object> resultMap = todayUserService.findByUserId(userId);
        return ResponseEntity.ok(resultMap);
    }

}
