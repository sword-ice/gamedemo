package com.xia.game.service;

import com.xia.game.dao.UserMapper;
import com.xia.game.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "user")
public class UserService {

    UserMapper userMapper;

    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Cacheable(key = "'user_cache_uid_'+ #uid")
    public User findUserById(int uid) {
        return userMapper.findUserById(uid);
    }

    @Cacheable(key = "'user_cache_username_'+ #p0")
    public User findUserByUsername(long username){
        return userMapper.findUserByUsername(username);
    }

    public List<User> findUserAll(){
        return userMapper.findUserAll();
    }

    @Cacheable(key = "'user_cache_uid_'+ #user.uid")
    public void insertUser(User user){
        userMapper.insertUser(user);
    }

    @Cacheable(key = "'user_cache_uid_'+ #uid")
    public void deleteUserById(int uid){
        userMapper.deleteUserById(uid);
    }

    @Cacheable(key = "'user_cache_uid_'+ #user.uid")
    public void updateUserPassword(User user){
        userMapper.updateUserPassword(user);
    }

}
