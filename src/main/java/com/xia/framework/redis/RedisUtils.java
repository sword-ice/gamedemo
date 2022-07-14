package com.xia.framework.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * @author xia
 * 使用springboot整合redis，此类暂不做使用
 */
@Component
@Deprecated
public class RedisUtils {
    private final RedisTemplate<String,Object> redisTemplate;

    private final ValueOperations<String, String> valueOperations;

    private HashOperations<String, String, Object> hashOperations;

    private ListOperations<String, Object> listOperations;

    private SetOperations<String, Object> setOperations;

    private ZSetOperations<String, Object> zSetOperations;

    @Autowired
    public RedisUtils(RedisTemplate<String,Object> redisTemplate,ValueOperations<String,String> valueOperations){
        this.redisTemplate = redisTemplate;
        this.valueOperations = valueOperations;
    }

    @Autowired
    public void setHashOperations(HashOperations<String, String, Object> hashOperations){
        this.hashOperations = hashOperations;
    }

    @Autowired
    public void setListOperations( ListOperations<String, Object> listOperations){
        this.listOperations = listOperations;
    }
    @Autowired
    public void setSetOperations( SetOperations<String, Object> setOperations){
        this.setOperations = setOperations;
    }
    @Autowired
    public void setzSetOperations(ZSetOperations<String, Object> zSetOperations){
        this.zSetOperations = zSetOperations;
    }
    /**  默认过期时长，单位：秒 */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**  不设置过期时长 */
    public final static long NOT_EXPIRE = -1;

    public boolean containsKey(String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void set(String key, Object value, long expire){
        valueOperations.set(key, toJson(value));
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value){
        set(key, value, DEFAULT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }

        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return JSONObject.parseObject(json,clazz);
    }
}
