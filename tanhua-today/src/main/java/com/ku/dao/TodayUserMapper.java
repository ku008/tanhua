package com.ku.dao;

import com.ku.bean.TodayUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ku
 * @date 2020/12/24
 */
@Repository
public interface TodayUserMapper extends MongoRepository<TodayUser,String> {
}
