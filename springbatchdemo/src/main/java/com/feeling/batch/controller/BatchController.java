package com.feeling.batch.controller;

import javax.annotation.Resource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchController {

	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	JobOperator jobOperator; 
	
	@Resource(name="messagebatchinsertjob")
	private Job batchJob;
	
	/**
	 * 每天读取txt文件，
	 * 并且把txt文件数据处理后保存到新的txt中
	 * 代表一个简单的界面来启动Job一个给定的一组 JobParameters
	 * JobLauncher.run(Job job, JobParameters jobParameters)
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0/1 * * * ?") 
	public void job3() throws Exception {
		JobExecution run = jobLauncher.run(batchJob, new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters());
		run.getId();
	}
	
	
	
	

}
