package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bean.RoleBean;
import com.bean.TaskBean;
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

	public void addTask(TaskBean taskBean) {
		
		stmt.update("insert into task (task_name,description,start_date,end_date,user_id,admin_id) values (?,?,?,?,?,?)",taskBean.getTask_name(),taskBean.getDescription(),taskBean.getStart_date(),taskBean.getEnd_date(),taskBean.getUser_id(),taskBean.getAdmin_id());
		
	}

	public TaskBean updateTask(TaskBean taskBean, int task_id) {
		
		System.out.println(taskBean.getEnd_date());
		int i=stmt.update("update task set task_name=?, description=?, start_date=?, end_date=?, user_id=?, admin_id=? where task_id=?",taskBean.getTask_name(),taskBean.getDescription(),taskBean.getStart_date(),taskBean.getEnd_date(),taskBean.getUser_id(),taskBean.getAdmin_id(),task_id);
		if(i==1)
			return viewTask(task_id);
		else
			return null;
	}

	public boolean deteleTask(int task_id) {
		
		int i=stmt.update("delete from task where task_id="+task_id);
		if(i==1)
			return true;
		else
			return false;
	}
	
	public ArrayList<TaskBean> listTasks() {

		ArrayList<TaskBean> list = (ArrayList<TaskBean>) stmt.query("select * from task", new Rm());
		return list;

	}

	class Rm implements RowMapper<TaskBean> {

		public TaskBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			TaskBean bean = new TaskBean();
			
			bean.setUser_id(rs.getInt("user_id"));
			bean.setTask_id(rs.getInt("task_id"));
			bean.setTask_name(rs.getString("task_name"));
			bean.setDescription(rs.getString("description"));
			bean.setStart_date(rs.getString("start_date"));
			bean.setEnd_date(rs.getString("end_date"));
			bean.setIsComplete(rs.getInt("iscomplete"));
			bean.setCompletion_date(rs.getString("completion_date"));
			bean.setComment(rs.getString("comment"));
			bean.setAdmin_id(rs.getInt("admin_id"));
			
			
			return bean;
		}

	}

	public TaskBean viewTask(int task_id) {
		//Try Catch because queryForObject return EmptyResultDataAccessException if it does not found record
		try {
			TaskBean task = stmt.queryForObject("select * from task where task_id  ="+ task_id +"",new BeanPropertyRowMapper<TaskBean>(TaskBean.class));
			return task;
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	}

	public TaskBean completeTask(int task_id) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String date_time = formatter.format(date);
		System.out.println(date_time);
		int i=stmt.update("update task set iscomplete=1,comment='',completion_date=? where task_id=?",date_time,task_id);
		if(i==1)
			return viewTask(task_id);
		else
			return null;
	}

	public TaskBean reopenTask(int task_id, TaskBean taskBean) {
		
		int i=stmt.update("update task set iscomplete=0, completion_date='', comment=? where task_id=? ",taskBean.getComment(),task_id);
		if(i==1)
			return viewTask(task_id);
		else
			return null;
	}

}
