package com.icat.quest.scheduler.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.icat.quest.common.utils.SchedulerConfigResolver;
import com.icat.quest.scheduler.service.impl.ExamQuestionCacheSchedulerJob;
import com.icat.quest.scheduler.service.impl.ExamStatusSchedulerJob;

@Configuration
@EnableTransactionManagement
public class QuartzConfig {
	
	
	@Bean("examStatusUpdateJob")
	public JobDetailFactoryBean examStatusUpdateJob() {
		JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
		jobDetailFactoryBean.setJobClass(ExamStatusSchedulerJob.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timeout", 5);
		jobDetailFactoryBean.setJobDataAsMap(map);
		jobDetailFactoryBean.setDurability(true);
		return jobDetailFactoryBean;
	}

	@Bean("examStatusUpdateJobTrigger")
	public CronTriggerFactoryBean examStatusUpdateJobTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(examStatusUpdateJob().getObject());
		cronTriggerFactoryBean
				.setCronExpression(SchedulerConfigResolver.getProperty("quartz.trigger.examStatusCronTrigger.cronExpression",""));
		return cronTriggerFactoryBean;
	}

	@Bean("examQuestionCacheJob")
	public JobDetailFactoryBean examQuestionCacheJob() {
		JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
		jobDetailFactoryBean.setJobClass(ExamQuestionCacheSchedulerJob.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timeout", 5);
		jobDetailFactoryBean.setJobDataAsMap(map);
		jobDetailFactoryBean.setDurability(true);
		return jobDetailFactoryBean;
	}

	@Bean("examQuestionCacheJobTrigger")
	public CronTriggerFactoryBean examQuestionCacheJobTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(examQuestionCacheJob().getObject());
		cronTriggerFactoryBean
				.setCronExpression(SchedulerConfigResolver.getProperty("quartz.trigger.examQuestionCronTrigger.cronExpression",""));
		return cronTriggerFactoryBean;
	}

	
	@Bean("dataSource")
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource
				.setDriverClassName(SchedulerConfigResolver.getProperty("quartz.datasource.driverClassName", ""));
		driverManagerDataSource.setUrl(SchedulerConfigResolver.getProperty("quartz.datasource.url", ""));
		driverManagerDataSource.setUsername(SchedulerConfigResolver.getProperty("quartz.datasource.username", ""));
		driverManagerDataSource.setPassword(SchedulerConfigResolver.getProperty("quartz.datasource.password", ""));
		return driverManagerDataSource;
	}

	@Bean("schedulerFactoryBean")
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setDataSource(dataSource());
		schedulerFactoryBean.setJobDetails(examStatusUpdateJob().getObject(), examQuestionCacheJob().getObject());
		schedulerFactoryBean.setTriggers(examStatusUpdateJobTrigger().getObject(), examQuestionCacheJobTrigger().getObject());

		Properties properties = new Properties();
		properties.setProperty("org.quartz.scheduler.instanceId",
				SchedulerConfigResolver.getProperty("quartz.scheduler.instanceId", ""));
		properties.setProperty("org.quartz.scheduler.instanceName",
				SchedulerConfigResolver.getProperty("quartz.scheduler.instanceName", ""));
		properties.setProperty("org.quartz.jobStore.class",
				SchedulerConfigResolver.getProperty("quartz.jobStore.class", ""));
		properties.setProperty("org.quartz.jobStore.driverDelegateClass",
				SchedulerConfigResolver.getProperty("quartz.jobStore.driverDelegateClass", ""));
		properties.setProperty("org.quartz.jobStore.tablePrefix",
				SchedulerConfigResolver.getProperty("quartz.jobStore.tablePrefix", ""));
		properties.setProperty("org.quartz.jobStore.isClustered",
				SchedulerConfigResolver.getProperty("quartz.jobStore.isClustered", ""));
		properties.setProperty("org.quartz.jobStore.clusterCheckinInterval",
				SchedulerConfigResolver.getProperty("quartz.jobStore.clusterCheckinInterval", ""));
		properties.setProperty("org.quartz.jobStore.misfireThreshold",
				SchedulerConfigResolver.getProperty("quartz.jobStore.misfireThreshold", ""));
		schedulerFactoryBean.setQuartzProperties(properties);

		return schedulerFactoryBean;
	}

}
