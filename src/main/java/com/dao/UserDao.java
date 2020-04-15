package com.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.UserDataBean;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import org.springframework.stereotype.Repository;

import com.bean.UserBean;


/*
 * add_user --> Aastha change_password --> Aastha update_profile --> Aastha
 * delete_user --> Pheni list_users --> Pheni view_user --> Pheni
 */

@Repository
public class UserDao {

	@Autowired
	JdbcTemplate stmt;




	public UserDataBean getUserDataByEmail(String email) {
		UserDataBean userDataBean;
		userDataBean = stmt.queryForObject("select * from users where email=\'" + email + "\'",
				new BeanPropertyRowMapper<UserDataBean>(UserDataBean.class));
		return userDataBean;
	}

	// Delete User by FirstName
	// Input: UserID
	// Output: Returns 1 if user is deleted, 0 if user is not deleted
	public int deleteUser(int id) {

		return stmt.update("delete from users where user_id = ?", id);

	}

	// List all Users
	// Input: Nothing
	// Output: ArrayList of Users
	public ArrayList<UserBean> listUsers() {

		ArrayList<UserBean> list = (ArrayList<UserBean>) stmt.query("select * from users", new Rm());
		return list;

	}

	class Rm implements RowMapper<UserBean> {

		public UserBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserBean bean = new UserBean();
			bean.setUser_id(rs.getInt("user_id"));
			bean.setFirst_name(rs.getString("first_name"));
			bean.setEmail(rs.getString("email"));
			bean.setPassword(rs.getString("password"));
			bean.setLast_name(rs.getString("last_name"));
			return bean;
		}

	}

	// Get Single User Detail by FirstName
	// Input:UserID
	// Output:Return details of user
	public UserBean getUserByFirstName(int id) {
		//Try Catch because queryForObject return EmptyResultDataAccessException if it does not found record
		try {
			UserBean user = stmt.queryForObject("select * from users where user_id  ="+id+"",
					new BeanPropertyRowMapper<UserBean>(UserBean.class));
			return user;
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}


	
	public int add_user(UserBean userBean)
	{
		int userid = 0;
		stmt.update("insert into users (first_name, last_name, email, password) values (?,?,?,?)", userBean.getFirst_name(), userBean.getLast_name(), userBean.getEmail(), userBean.getPassword());
		return userid;
	}
  
	  public boolean update_profile(UserBean userBean)
	  {
		  int i= stmt. update("update users set first_name = ?, last_name= ?, email= ?, password = ? where user_id = ? ",userBean.getFirst_name(), userBean.getLast_name(), userBean.getEmail(),userBean.getPassword(), userBean.getUser_id());
		  if(i==1)
			  return true;
		  else 
			  return false;
	  }

	public boolean change_password(int id, String oldPassword, String newPassword) {

		class MyRowMapper implements RowMapper<UserBean>
		{

			@Override
			public UserBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserBean userBean  = new UserBean();
				userBean.setEmail(rs.getString("email"));
				userBean.setPassword(rs.getString("password"));
				userBean.setFirst_name(rs.getString("first_name"));
				userBean.setLast_name(rs.getString("last_name"));
				return userBean;
				
			}
			
		}
		
		
		ArrayList<UserBean> list= (ArrayList<UserBean>) stmt.query("select * from users where user_id="+id+" and password = '"+oldPassword+"'",new MyRowMapper());
		System.out.println(list.size());
		if(list.size()==1)
		{
		int rows = stmt.update("update users set password=? where user_id=?",newPassword,id);
		System.out.println("list returned from the database ");
		if(rows>=1)
		{ System.out.println("updated in the database");
		return false;
		}
		}

		return true;
		}
}
