package com.ku.service;

import com.ku.bean.TodayUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ku
 * @date 2020/12/20
 */
@Service
public class TodayUserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insert(TodayUser todayUser){
        mongoTemplate.insert(todayUser);
    }

    public List<TodayUser> findall(){
        String dbName = mongoTemplate.getDb().getName();
        System.out.println("数据库名为："+dbName);
        List<TodayUser> list = mongoTemplate.findAll(TodayUser.class, "today");
        System.out.println(list);
        return list;
    }

    public TodayUser findByPhone(String phone){
        Query query = new Query(Criteria.where("phone").is(phone));
        TodayUser todayUser = mongoTemplate.findOne(query, TodayUser.class,"today");
        System.out.println(todayUser);
        return todayUser;
    }

    public void updateUser(TodayUser todayUser) {
        Query query = new Query(Criteria.where("id").is("1"));
        Update update = new Update().set("userId", "1")
                .set("name", todayUser.getName())
                .set("sex", todayUser.getSex())
                .set("age", todayUser.getAge())
                .set("email", todayUser.getEmail())
                .set("avatar", todayUser.getAvatar())
                .set("toUserId", todayUser.getToUserId())
                .set("score", todayUser.getScore());
        mongoTemplate.updateFirst(query, update, TodayUser.class);
    }

    public void deleteUserById(Integer id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, TodayUser.class);
    }

    public Map<String, Object> findByMaxScore() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC,"score")).limit(1);
        Map todayMap = mongoTemplate.findOne(query, Map.class,"today");
        Map<String,Object> resultMap = new HashMap<>();
        if (todayMap != null){
            resultMap.put("code",200);
            resultMap.put("msg","推荐家人成功...");
            resultMap.put("data",todayMap);
        }else{
            resultMap.put("code",201);
            resultMap.put("msg","暂无家人推荐...");
        }
        return resultMap;
    }

    public Map<String, Object> findByUserId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        query.with(Sort.by(Sort.Direction.DESC,"score"));
        List<Map> list = mongoTemplate.find(query, Map.class, "today");
        System.out.println(list);
        Map<String,Object> resultMap = new HashMap<>();
        if (list != null){
            resultMap.put("code",200);
            resultMap.put("msg","推荐家人们成功...");
            resultMap.put("data",list);
        }else{
            resultMap.put("code",201);
            resultMap.put("msg","暂无家人们推荐...");
        }
        return resultMap;
    }

}
