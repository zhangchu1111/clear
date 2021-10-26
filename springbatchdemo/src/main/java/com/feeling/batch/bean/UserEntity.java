package com.feeling.batch.bean;

import java.util.Date;

public class UserEntity {

	private String username;
	private int age;
	private char sex;
	private Date birthday;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public UserEntity(String username, int age, char sex, Date birthday) {
		super();
		this.username = username;
		this.age = age;
		this.sex = sex;
		this.birthday = birthday;
	}

	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
