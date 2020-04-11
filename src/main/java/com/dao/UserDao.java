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

}
