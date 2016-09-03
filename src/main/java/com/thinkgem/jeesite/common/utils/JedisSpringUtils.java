package com.thinkgem.jeesite.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;
import java.util.List;
/**
 * Created by tangaoyu on 2016/9/3.
 * */


public class JedisSpringUtils {
    @Autowired
    private  RedisTemplate redisTemplate;

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void put(final String key, final Serializable cacheObject) {
        redisTemplate.execute(new RedisCallback<Serializable>() {
            @Override
            public Serializable doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<Serializable> value = (RedisSerializer<Serializable>) redisTemplate.getValueSerializer();
                connection.set(redisTemplate.getStringSerializer().serialize(key), value.serialize(cacheObject));
                return null;
            }
        });
    }
    public Object get(final String key) {
        return redisTemplate.execute(new RedisCallback<Serializable>() {
            @Override
            public Serializable doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] redisKey = redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(redisKey)) {
                    byte[] value = connection.get(redisKey);
                    Serializable valueSerial = (Serializable)redisTemplate.getValueSerializer()
                            .deserialize(value);
                    return valueSerial;
                }
                return null;
            }
        });
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }

    public void put(final String key, final Serializable cacheObject,final long timeout) {
        redisTemplate.execute(new RedisCallback<Serializable>() {
            @Override
            public Serializable doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<Serializable> value = (RedisSerializer<Serializable>) redisTemplate.getValueSerializer();
                connection.set(redisTemplate.getStringSerializer().serialize(key), value.serialize(cacheObject));
                if(timeout>0){
                    connection.expire(redisTemplate.getStringSerializer().serialize(key), timeout);
                }
                return null;
            }
        });
    }

/**
     * 设置list缓存
     * @param key	键
     * @param value	集合
     * @param cacheSeconds 缓存时间  时间<1则不设置失效时间*/


    public <T> void setObjectList(final String key, final List<T> value, final int cacheSeconds) {
        redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                if (connection.exists(getBytesKey(key))) {
                    connection.del(getBytesKey(key));
                }
                byte[] bytes = toBytes(value);
                connection.set(getBytesKey(key),bytes);
                if(cacheSeconds>0){
                    connection.expire(getBytesKey(key), cacheSeconds);
                }
                return null;
            }
        });
    }

/**
     * 根据key获取list数据
     * @param key
     * @return*/


    public <T> List<T> getObjectList(final String key){
        List<T> execute = (List<T>) redisTemplate.execute (new RedisCallback<List<T>>() {
            @Override
            public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
                List<T> cacheValue = null;
                byte[] redisKey = getBytesKey(key);
                if (connection.exists(redisKey)) {
                    byte[] bs = connection.get(redisKey);
                    if(null!= bs){
                        cacheValue = (List<T>) toObject(bs);
                    }
                    return cacheValue;
                }
                return null;
            }
        });
        return execute;
    }

/**
     * 获取byte[]类型Key
     * @param object
     * @return*/


    private byte[] getBytesKey(Object object){
        if(object instanceof String){
            return redisTemplate.getStringSerializer().serialize((String) object);
        }else{
            return ObjectUtils.serialize(object);
        }
    }
/*
*
     * Object转换byte[]类型
     * @param object
     * @return
*/


    private byte[] toBytes(Object object){
        return ObjectUtils.serialize(object);
    }

/**
     * byte[]型转换Object
     * @param bytes
     * @return*/


    private Object toObject(byte[] bytes){
        return ObjectUtils.unserialize(bytes);
    }
}
