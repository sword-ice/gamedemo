package com.xia.game.dao;

import com.xia.game.entity.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.redis.RedisCache;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@CacheNamespace(implementation = RedisCache.class)
public interface UserMapper {
    User findUserById(int id);
    List<User> findUserAll();
    void insertUser(User user);
    void deleteUserById(int id);
    void updateUserPassword(User user);
    User findUserByUsername(long username);
}
