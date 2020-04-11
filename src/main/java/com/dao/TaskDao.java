package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bean.RoleBean;
import com.bean.UserBean;

@Repository
public class TaskDao {
	@Autowired
	JdbcTemplate stmt;
	public List<UserBean> getAdminList() {
		
		int role = getRoleId("admin");
		if(role==-1) {
			System.out.println("error in finding role");
			return null;
		}
		else {
			List<UserBean> adminList = new ArrayList<>(); 
			System.out.println("Role "+(role+1));
			String sql = "select * from userr where role_id = " + (role+1);
			adminList= stmt.query(sql,new UserRowMapper());
			return adminList;
		}
	}
	class UserRowMapper implements RowMapper<UserBean>{

		@Override
		public UserBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserBean userBean = new UserBean();
				userBean.setUser_id(rs.getInt("user_id"));
				userBean.setFirst_name(rs.getString("first_name"));
				userBean.setLast_name(rs.getString("last_name"));
				userBean.setEmail(rs.getString("email"));
				userBean.setPassword("password");
				
			return userBean;
		}
		
	}

	private int getRoleId(String roleName) {
		
		String sql = "select * from role where role_name="+"'"+roleName+"'";
		RoleBean roleBean = stmt.queryForObject(sql, new BeanPropertyRowMapper<RoleBean>(RoleBean.class));
		System.out.println("Role 2 "+roleBean.getRoleName());
		return roleBean!=null?roleBean.getRoleID():-1;
	}

}
