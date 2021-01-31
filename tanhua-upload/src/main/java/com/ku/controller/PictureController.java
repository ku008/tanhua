package com.ku.controller;

import com.ku.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ku
 * @date 2020/12/25
 */
@Controller
@RequestMapping("/picture")
@CrossOrigin
@MultipartConfig
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @PostMapping("/uploadOne")
    public ResponseEntity<String> uploadOne( MultipartFile file){
        String objectId = pictureService.uploadOne(file);
        System.out.println("单个图片存储成功...");
        return ResponseEntity.ok(objectId);
    }

    @PostMapping("/uploadMore")
    public ResponseEntity<List<String>> uploadMore( MultipartFile[] files){
        System.out.println("多图片："+files);
        List<String> objectIdList = pictureService.uploadMore(files);
        System.out.println("批量图片存储成功...");
        return ResponseEntity.ok(objectIdList);
    }

    @GetMapping("/getOne")
    public ResponseEntity<Map<String,Object>> getOne(@RequestParam String objectId, HttpServletResponse response) throws IOException {
        Map<String,Object> resultMap = pictureService.getOne(objectId,response);
        System.out.println("单个图片查询成功...");
        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("/deleteOne")
    public ResponseEntity<Map<String,Object>> deleteOne(@RequestParam String objectId) throws IOException {
        Map<String,Object> resultMap = new HashMap<>();
        pictureService.deleteOne(objectId);
        System.out.println("单个图片删除成功...");
        resultMap.put("code",1);
        return ResponseEntity.ok(resultMap);
    }

}
