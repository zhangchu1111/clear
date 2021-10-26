package com.feeling.batch.proccess;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.feeling.batch.bean.UserEntity;

@Component
public class BatchItemProcessor implements ItemProcessor<UserEntity, UserEntity> {

	@Override
	public UserEntity process(UserEntity user) throws Exception {
		// TODO Auto-generated method stub
		return user;
	}
	 

}
