package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.UserDataBean;


@Repository
public class UserDao {	

	@Autowired
	JdbcTemplate stmt;

	public UserDataBean getUserDataByEmail(String email) {
		UserDataBean userDataBean ;
		userDataBean =stmt.queryForObject("select * from users where email=\'"+ email + "\'", new BeanPropertyRowMapper<UserDataBean>(UserDataBean.class));
		return userDataBean;		
	}


 }



