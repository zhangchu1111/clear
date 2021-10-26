package com.feeling.batch.job;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.BindException;

import com.feeling.batch.bean.UserEntity;
import com.feeling.batch.exception.BatchStepExceptionHandler;
import com.feeling.batch.listener.BatchJobListener;
import com.feeling.batch.proccess.BatchItemProcessor;
import com.feeling.batch.util.DateUtil;
import com.feeling.batch.writer.BatchItemWriter;
@Configuration
public class BatchJob {
	private static final Logger logger = LoggerFactory.getLogger(BatchJob.class);
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	public PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	public BatchStepExceptionHandler exceptionHandler;

	@Autowired
	public BatchItemWriter batchitemwriter;
	
	@Autowired
	public BatchItemProcessor batchitemprocessor;

	
	
	/**
	 * 构建job
	 * 创建bean，然后用@Resource(name="batchJob")创建对象
	 *  1、当第二天重启前一天的任务时！！！文件日期有异
	 * @param listener
	 * @return
	 */
	@Bean("messagebatchinsertjob")
	public Job MessageBatchInsertJob(BatchJobListener listener) {
		return jobBuilderFactory.get("MessageBatchInsertJob").listener(listener).flow(MessageBatchInsertStep()).end()
				.build();
	}
	
	/**
	 * 声明发送到MQ step
	 * 1、Skip:如果处理过程中某条记录是错误的,如CSV文件中格式不正确的行,那么可以直接跳过该对象,继续处理下一个。 
	 * 2、在chunk元素上定义skip-limit属性,告诉Spring最多允许跳过多少个items,超过则job失败
	 * 3、Restart:如果将job状态存储在数据库中,而一旦它执行失败,	那么就可以选择重启job实例,	并继续上次的执行位置。
	 * 4、最后,对于执行失败的job作业,我们可以重新启动,并让他们从上次断开的地方继续执行。要达到这一点,只需要使用和上次 一模一样的参数来启动job,	
	 * 则Spring	Batch会自动从数据库中找到这个实例然后继续执行。你也可以拒绝重启,或者参数控 制某个	job中的一个tep可以重启的次数(一般来说多次重试都失败了,那我们可能需要放弃。)
	 *
	 * @return
	 */
	@Bean
	public Step MessageBatchInsertStep() {
		logger.info("MessageBatchInsertStep");
		return stepBuilderFactory.get("MessageBatchInsertStep").<UserEntity, UserEntity>chunk(100).reader(fileRead()).processor(batchitemprocessor)
				.writer(batchitemwriter).faultTolerant().skip(Exception.class).skipLimit(100)
				.taskExecutor(new SimpleAsyncTaskExecutor()).startLimit(10).allowStartIfComplete(true)
				.exceptionHandler(exceptionHandler) // 设置并发方式执行exceptionHandler,异常时打印日志并抛出异常
				.throttleLimit(10) // 并发任务数为 10,默认为4
				.transactionManager(platformTransactionManager).build();
	}
	
  
	public FlatFileItemReader<UserEntity> fileRead() {
		System.out.println("fileRead()方法开始");

		FlatFileItemReader<UserEntity> fileRead = new FlatFileItemReader<>();
		fileRead.setEncoding("UTF-8");
		fileRead.setResource(new FileSystemResource(new File("E:\\user.txt")));
		//fileRead.setLinesToSkip(2);跳过开头多少行
		DefaultLineMapper<UserEntity> lineMapper = new DefaultLineMapper<UserEntity>();
		lineMapper.setLineTokenizer(new DelimitedLineTokenizer(","));
		lineMapper.setFieldSetMapper(new FieldSetMapper<UserEntity>() {
			
			@Override
			public UserEntity mapFieldSet(FieldSet fieldSet) throws BindException {
				UserEntity user = new UserEntity();
				try {
					user.setUsername(fieldSet.readString(0));
	                user.setAge(fieldSet.readInt(1));
	                user.setSex(fieldSet.readChar(2));
	                user.setBirthday(DateUtil.parseDate(fieldSet.readString(3)));
					
				} catch (Exception e) {
					logger.error("解析异常："+e.getMessage());
				}
				return user;
			}
		});
		fileRead.setLineMapper(lineMapper);
		return fileRead;
	}
   

    

  
}
