package com.ku.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ku.config.JwtUtils;
import com.ku.dao.UserInfoMapper;
import com.ku.dao.UserMapper;
import com.ku.pojo.User;
import com.ku.pojo.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ku
 * @date 2020/12/13
 */
@Service
public class UserService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expireMinutes}")
    private int expireMinutes;

    @Autowired
    private StringRedisTemplate stringRedisTemplate = null;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    public Map<String, Object> verifyUser(Map<String, Object> parmas) {
        Object phone = parmas.get("phone");
        Object verifyCode = parmas.get("verifyCode");
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(phone))) {
            resultMap.put("code", 205);
            resultMap.put("msg", "验证码已失效，请重新发送...");
            return resultMap;
        }

        String verifyCodeRedis = stringRedisTemplate.opsForValue().get(phone);
        if (!verifyCode.equals(verifyCodeRedis)) {
            resultMap.put("code", 205);
            resultMap.put("msg", "验证码错误...");
        } else {
            User user = new User();
            user.setId(getMaxId() + 1);
            user.setPhone((String) phone);
            //使用MyBatiesPlus查询数据库
            QueryWrapper queryUser = new QueryWrapper();
            queryUser.eq("phone",phone);
            User dbUser = userMapper.selectOne(queryUser);
            boolean flag = false;
            if (dbUser == null) {
                user.setPassword(DigestUtils.md5DigestAsHex("111111".getBytes()));
                userMapper.insert(user);
            }
            dbUser = userMapper.selectOne(queryUser);

            QueryWrapper queryUserInfo = new QueryWrapper();
            queryUserInfo.eq("phone",phone);
            UserInfo dbUserInfo = userInfoMapper.selectOne(queryUserInfo);
            if (dbUserInfo == null) {
                flag = true;
            }

            String token = JwtUtils.generateToken(parmas, secret, expireMinutes);
            stringRedisTemplate.opsForValue().set("token_" + phone, token, expireMinutes, TimeUnit.MINUTES);

            resultMap.put("code", 200);
            resultMap.put("flag", flag);
            resultMap.put("msg", "用户注册成功...");
            resultMap.put("token", token);
            resultMap.put("data", dbUser);
        }
        return resultMap;
    }

    public Integer getMaxId() {
        QueryWrapper queryUser = new QueryWrapper();
        queryUser.orderByDesc("id");
        queryUser.last("limit 1");
        User user = userMapper.selectOne(queryUser);
        return user.getId();
    }

}
