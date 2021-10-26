package com.feeling.batch.writer;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.feeling.batch.bean.UserEntity;

@Component
@StepScope
public class BatchItemWriter implements ItemWriter<UserEntity> {

   



    @Override
    public void write(List<? extends UserEntity> users){
      for (UserEntity user : users) {
    	  
		System.out.println(user.toString());
	}
    }
}
