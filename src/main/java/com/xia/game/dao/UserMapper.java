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
    public User findUserById(int id);
    public List<User> findUserAll();
    public void insertUser(User user);
    public void deleteUserById(int id);
    public void updateUserPassword(User user);
    public User findUserByUsername(long username);
}
