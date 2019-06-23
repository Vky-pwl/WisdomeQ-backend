package com.icat.quest.scheduler.service.impl;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.icat.quest.common.utils.SpringApplicationContext;
import com.icat.quest.dao.user.service.ExamQuestionCacheService;

public class ExamStatusSchedulerJob extends QuartzJobBean implements Job {
	
	private ExamQuestionCacheService		examQuestionCacheService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamStatusSchedulerJob.class);
	
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if(examQuestionCacheService == null) {
		this.examQuestionCacheService = (ExamQuestionCacheService) SpringApplicationContext.getBean("examQuestionCacheService");
		}
		LOGGER.debug("Exam Status Job: "+ new Date());
		if(examQuestionCacheService != null) {
		examQuestionCacheService.updateExamStatusBatchUpdate();
		}
	}

}
