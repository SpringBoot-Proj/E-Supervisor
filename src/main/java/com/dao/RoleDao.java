package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bean.RoleBean;
import com.bean.UserBean;

@Repository
public class RoleDao {

	@Autowired
	JdbcTemplate stmt;

	public boolean addRoleType(UserBean userBean,String roleType) {

		RoleBean roleBean = null;
		roleBean = getRole(roleType);
		int rowsAffected = stmt.update("insert into users_role(userid,roleid) values(?,?)",userBean.getUser_id(),roleBean.getRoleID());
		
		if(rowsAffected>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public ArrayList<RoleBean> listRoles()
	{
		
		String sql = "select * from role";
		ArrayList<RoleBean> roles = (ArrayList<RoleBean>) stmt.query(sql,new MyRowMapper());
		return roles ;
	}
	
	class MyRowMapper implements RowMapper<RoleBean> {

		@Override
		public RoleBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			RoleBean roleBean = new RoleBean();
			roleBean.setRoleID(rs.getInt("roleid"));
			roleBean.setRoleName(rs.getString("rolename"));

			return roleBean;
		}

	}
	
	public RoleBean getRole(String roleType) {
		
		String sql="select * from role where rolename=?";
		RoleBean roleBean = stmt.queryForObject(sql, new Object[] {roleType
				},new MyRowMapper());
		
		return roleBean;
	}

}
