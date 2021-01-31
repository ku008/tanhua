package com.ku.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ku.dao.UserInfoMapper;
import com.ku.pojo.User;
import com.ku.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ku
 * @date 2020/12/19
 */
@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public Map<String, Object> registerUser(UserInfo userInfo) {
        //使用MyBatiesPlus插入数据
        String phone = userInfo.getPhone();
        userInfo.setId(getMaxId() + 1);
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());
        QueryWrapper queryWrapper = new QueryWrapper(new UserInfo(phone));
        UserInfo dbUserInfo = userInfoMapper.selectOne(queryWrapper);
        Map<String, Object> resultMap = new HashMap<>();
        if (dbUserInfo != null) {
            resultMap.put("code", 302);
            resultMap.put("msg", "用户:" + phone + "详细信息已存在...");
            return resultMap;
        }

        userInfoMapper.insert(userInfo);
        System.out.println("用户:" + userInfo.getPhone() + "-详细信息写入成功...");

        resultMap.put("code", 200);
        resultMap.put("msg", "详细信息写入成功...");
        dbUserInfo = userInfoMapper.selectOne(queryWrapper);
        resultMap.put("data", dbUserInfo);
        System.out.println("查询到的用户详细信息：" + dbUserInfo);
        return resultMap;
    }

    public Integer getMaxId() {
        QueryWrapper queryUser = new QueryWrapper();
        queryUser.orderByDesc("id");
        queryUser.last("limit 1");
        UserInfo userInfo = userInfoMapper.selectOne(queryUser);
        return userInfo.getId();
    }

}
