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

	public void addRoleType(RoleBean roleBean) {

		
		stmt.update("insert into users_role(role_name,role_id) values(?,?)",roleBean.getRole_name(),
				roleBean.getRole_id());

		
	}

	public ArrayList<RoleBean> listRoles() {

		String sql = "select * from users_role";
		ArrayList<RoleBean> roles = (ArrayList<RoleBean>) stmt.query(sql, new MyRowMapper());
		return roles;
	}

	class MyRowMapper implements RowMapper<RoleBean> {

		@Override
		public RoleBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			RoleBean roleBean = new RoleBean();
			roleBean.setRole_id(rs.getInt("role_id"));
			roleBean.setRole_name(rs.getString("role_name"));

			return roleBean;
		}

	}

	public RoleBean getRole(String roleType) {

		String sql = "select * from users_role where role_name=?";
		RoleBean roles = (RoleBean) stmt.query(sql, new MyRowMapper());

		return roles;
	}

	public RoleBean updateRoleType(RoleBean roleBean,int role_id) {

		
	//	roles = getRole(role_name);
		int rowsAffected = stmt.update("update users_role set role_name=? where role_id=?",roleBean.getRole_name(),roleBean.getRole_id()
				);

		if (rowsAffected == 1) {
			return roleBean;
		} else {
			return null;
		}

	}

	public boolean deteleRole(int role_id) {
		
		int i=stmt.update("delete from users_role where role_id="+role_id);
		if(i==1)
			return true;
		else
			return false;
	}

}
