package com.icat.quest.cache.utils;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class RedisJedisConnectionFactory extends JedisConnectionFactory{

	public RedisJedisConnectionFactory(RedisStandaloneConfiguration standaloneConfig) {
		super(standaloneConfig);
		// TODO Auto-generated constructor stub
	}

	
	
}
