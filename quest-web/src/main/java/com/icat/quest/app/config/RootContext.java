package com.icat.quest.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import com.icat.quest.common.utils.MailerConfigResolver;
import com.icat.quest.common.utils.RedisCacheConfigResolver;
import com.icat.quest.common.utils.SchedulerConfigResolver;
import com.icat.quest.common.utils.SystemConfigResolver;
import com.icat.quest.scheduler.config.QuartzConfig;

@Configuration
@Import(QuartzConfig.class)
public class RootContext {

	
	
	@Bean("systemConfigResolver")
	public  SystemConfigResolver systemConfigResolver()
	{
	   SystemConfigResolver systemConfigResolver = new SystemConfigResolver();
		systemConfigResolver.setLocation(new ClassPathResource("/systemconfig.properties"));
		systemConfigResolver.setIgnoreResourceNotFound(false);
		systemConfigResolver.setIgnoreUnresolvablePlaceholders(true);
		systemConfigResolver.setSearchSystemEnvironment(false);
		return systemConfigResolver;
	}
   
	@Bean("redisCacheConfigResolver")
	public  RedisCacheConfigResolver redisCacheConfigResolver()
	{
		RedisCacheConfigResolver redisCacheConfigResolver = new RedisCacheConfigResolver();
		redisCacheConfigResolver.setLocation(new ClassPathResource("/redis.properties"));
		redisCacheConfigResolver.setIgnoreResourceNotFound(false);
		redisCacheConfigResolver.setIgnoreUnresolvablePlaceholders(true);
		redisCacheConfigResolver.setSearchSystemEnvironment(false);
		return redisCacheConfigResolver;
	}
	
	@Bean("schedulerConfigResolver")
	public  SchedulerConfigResolver schedulerConfigResolver()
	{
		SchedulerConfigResolver schedulerConfigResolver = new SchedulerConfigResolver();
		schedulerConfigResolver.setLocation(new ClassPathResource("/schedulerconfig.properties"));
		schedulerConfigResolver.setIgnoreResourceNotFound(false);
		schedulerConfigResolver.setIgnoreUnresolvablePlaceholders(true);
		schedulerConfigResolver.setSearchSystemEnvironment(false);
		return schedulerConfigResolver;
	}
   
	@Bean("mailerConfigResolver")
	public  MailerConfigResolver mailerConfigResolver()
	{
		MailerConfigResolver mailerConfigResolver = new MailerConfigResolver();
		mailerConfigResolver.setLocation(new ClassPathResource("/mailerconfig.properties"));
		mailerConfigResolver.setIgnoreResourceNotFound(false);
		mailerConfigResolver.setIgnoreUnresolvablePlaceholders(true);
		mailerConfigResolver.setSearchSystemEnvironment(false);
		return mailerConfigResolver;
	}
   
}
