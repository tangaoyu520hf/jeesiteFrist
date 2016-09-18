package com.thinkgem.jeesite.JedisTest;

import com.thinkgem.jeesite.common.utils.JedisSpringUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import javax.sql.DataSource;
import java.io.Serializable;

/**
 * Spring 单元测试基类
 * @author ThinkGem
 * @version 2013-05-15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context-jedis.xml"})
public class SpringTransactionalContextTests {
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private RedisTemplate redisTemplate;
	@Test
	public void testRedis(){
		Jedis jedis = new Jedis("192.168.214.128", 6379);
		jedis.auth("123456");
		jedis.set("shenlinnan", "hahahahahaha");
		System.out.println(jedis.get("shenlinnan"));
		jedis.close();
/*		String tangaoyu = set("tangaoyu","草泥马",0);
		tangaoyu = get("tangaoyu");
		System.out.println(tangaoyu);*/
		JedisSpringUtils jedisSpringUtils = new JedisSpringUtils();
		jedisSpringUtils.setRedisTemplate(redisTemplate);
		jedisSpringUtils.put("tangaoyu","123456");
		Object tangaoyu1 = jedisSpringUtils.get("tangaoyu");
	}
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public String get(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.get(key);
				value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
			}
		} catch (Exception e) {
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	/**
	 * 获取资源
	 * @return
	 * @throws JedisException
	 */
	public Jedis getResource() throws JedisException {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
//			logger.debug("getResource.", jedis);
		} catch (JedisException e) {
			/*logger.warn("getResource.", e);*/
			returnBrokenResource(jedis);
			throw e;
		}
		return jedis;
	}
	/**
	 * 归还资源
	 * @param jedis
	 */
	public void returnBrokenResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnBrokenResource(jedis);
		}
	}
	/**
	 * 释放资源
	 * @param jedis
	 */
	public void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public String set(String key, String value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.set(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return result;
	}
}
