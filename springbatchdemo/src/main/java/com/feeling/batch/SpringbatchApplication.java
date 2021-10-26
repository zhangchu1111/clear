package com.feeling.batch;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan(basePackages = { "com.feeling.batch.*" }) // 将该包下的文件纳入容器中
@EnableAutoConfiguration
@EnableBatchProcessing//springbatch
@EnableScheduling//定时器
@MapperScan(basePackages = { "com.feeling.batch.mapper" })//mybatis的Mapper层扫描
public class SpringbatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}
	/**
	 * JobRepository是上述所有定型的持久机制。它提供了CRUD操作JobLauncher，Job以及 Step实现。
	 * 当 Job第一次启动，一个 JobExecution被从库中获得，和执行过程中StepExecution和 JobExecution实现方式是通过将它们传递到存储库坚持：
	 * @param dataSource
	 * @param transactionManager
	 * @return
	 */
	@Bean  
    public JobRepository jobRepositoryFactoryBean(DataSource dataSource,PlatformTransactionManager transactionManager){  
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();  
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);  
        jobRepositoryFactoryBean.setDataSource(dataSource);  
        jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");  
        try {  
            jobRepositoryFactoryBean.afterPropertiesSet();
            return  jobRepositoryFactoryBean.getObject();
        } catch (Exception e) {  
            //logger.error("创建jobRepositoryFactoryBean异常：{}",e.getMessage());  
        }  
        return null;  
    }

}
