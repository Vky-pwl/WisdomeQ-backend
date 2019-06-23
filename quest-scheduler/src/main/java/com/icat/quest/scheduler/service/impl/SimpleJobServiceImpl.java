/*package com.icat.quest.scheduler.service.impl;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icat.quest.scheduler.service.SimpleJobService;


@Component
public class SimpleJobServiceImpl implements SimpleJobService {

	@Autowired
	private StdScheduler sf ;
	
	@Override
	public void setJob(Integer examId, Date examDate) 
	{
		try 
		{
			//Set Data in JobDataMap to use during execution			
			JobDataMap jobDataMap=new JobDataMap();
			
			jobDataMap.put("examId", examId);
			jobDataMap.put("examDate", examDate);
			
			//Create Dynamic Job and it will autometically persist in Quartz database as soon as Job is end 
			JobDetail job = newJob(ExamStatusSchedulerJob.class)		
					.setJobData(jobDataMap)
					.withIdentity("examJob"+examId, "examJob") // name "myJob", group "group1"
					.build();
					
			
			
			Trigger trigger = newTrigger()
					.withIdentity("examJobTriger"+examId, "examJob")					
					.startAt(examDate)
					.withPriority(1)
					.withSchedule(simpleSchedule()
							.withIntervalInHours(0)
							.withRepeatCount(0))						
					.build();

			
			TriggerKey triggerKey = new TriggerKey("examJobTriger"+examId, "examJob");
			Trigger oldTrigger = sf.getTrigger(triggerKey);

			if(oldTrigger != null)
				sf.rescheduleJob(triggerKey, trigger);

			else
			sf.scheduleJob(job, trigger);	
	
			
		} catch (SchedulerException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
*/