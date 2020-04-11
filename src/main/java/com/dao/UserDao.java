package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.UserBean;

@Repository
public class UserDao {

	@Autowired
	JdbcTemplate stmt;
	
	public int add_user(UserBean userBean)
	{
		int userid = 0;
		stmt.update("insert into users (first_name, last_name, email, password) values (?,?,?,?)", userBean.getFirst_name(), userBean.getLast_name(), userBean.getEmail(), userBean.getPassword());
		return userid;
	}
	
	public boolean update_profile(UserBean userBean)
	{
		int i= stmt.update("update users set first_name = ?, last_name= ?, email= ?, password = ? where user_id = ? ",userBean.getFirst_name(), userBean.getLast_name(), userBean.getEmail(), userBean.getPassword(), userBean.getUser_id());
		
		if(i==1)
			return true;
		else
			return false;
	}
	
}
