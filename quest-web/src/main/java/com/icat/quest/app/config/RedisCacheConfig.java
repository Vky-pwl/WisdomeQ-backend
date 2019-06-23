package com.icat.quest.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.icat.quest.cache.utils.RedisCacheTemplate;
import com.icat.quest.cache.utils.RedisJedisConnectionFactory;
import com.icat.quest.cache.utils.RedisSerializerImpl;
import com.icat.quest.common.utils.RedisCacheConfigResolver;

@Configuration
public class RedisCacheConfig {

	@Bean("redisJedisConnectionFactory")
	public RedisJedisConnectionFactory redisJedisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		config.setHostName(RedisCacheConfigResolver.getProperty("redis.host", ""));
		config.setPort(Integer.parseInt(RedisCacheConfigResolver.getProperty("redis.port", "")));
		RedisJedisConnectionFactory jedisConnectionFactory = new RedisJedisConnectionFactory(config);
		jedisConnectionFactory.afterPropertiesSet();
		return jedisConnectionFactory;
	}

	@Bean("redisCacheTemplate")
	public RedisCacheTemplate<String, Object> redisCacheTemplate() {
		RedisCacheTemplate<String, Object> redisTemplate = new RedisCacheTemplate<String, Object>();
		redisTemplate.setConnectionFactory(redisJedisConnectionFactory());
		redisTemplate.setEnableDefaultSerializer(true);
		redisTemplate.setDefaultSerializer(redisMyOwnSerizable());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@Bean("redisStringSerializer")
	public RedisSerializer<String> redisStringSerializer() {
		return new StringRedisSerializer();
	}

	@Bean("redisMyOwnSerizable")
	public RedisSerializer<Object> redisMyOwnSerizable() {
		return new RedisSerializerImpl();
	}

	@Bean("redisMessageListener")
	public MessageListenerAdapter redisMessageListener() {
		return new MessageListenerAdapter(new RedisCacheMessageListener());
	}

	@Bean("redisContainer")
	public RedisMessageListenerContainer pushcordRedisContainer() {
		final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisJedisConnectionFactory());
		container.addMessageListener(redisMessageListener(), new ChannelTopic("my-app"));
		return container;
	}

}
