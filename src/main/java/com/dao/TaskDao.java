package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	ArrayList<Long> days=new ArrayList<>();
	public List<TaskBean> getUnderPerfTasks() {
		days.clear();
		List<TaskBean> taskList = new ArrayList<>();
		String query="select * from task where (current_date>end_date)  and (completion_date is null or end_date<completion_date)";
		taskList = stmt.query(query,new TaskRowMapper());
		List<KeyValue> myList = new ArrayList<>();
		System.out.println(days);
		for(int i=0;i<days.size();i++)
		{
			myList.add(new KeyValue(i,days.get(i)));
		}
		Collections.sort(myList, new Comparator<KeyValue>(){
            @Override
            public int compare(KeyValue arg0, KeyValue arg1) {
                return Long.compare(arg0.getValue(), arg1.getValue());
            }
        });
		List<TaskBean> taskList2 = new ArrayList<>();
		for(KeyValue kv:myList)
		{
			taskList2.add(taskList.get(kv.getKey()));
		}
		return taskList2;
	}
	class KeyValue {
	    private int key;
	    private Long value;

	    public KeyValue(int i, Long j) {
	        key  = i;
	        value = j;
	    }
	    public int getKey() {
	        return key;
	    }
	    public void setKey(int key) {
	        this.key = key;
	    }

	    public Long getValue() {
	        return value;
	    }

	    public void setValue(Long value) {
	        this.value = value;
	    }

	    @Override
	    public String toString(){
	        return "("+key+","+value.toString()+")";                
	    }
	}
	public HashMap<UserBean,List<TaskBean>> getAdminList() {	
		int role = getRoleId("admin");
		if(role==-1) {
			System.out.println("error in finding role");
			return null;
		}
		else {
			List<UserBean> adminList = new ArrayList<>(); 
			List<TaskBean> taskList;
			HashMap<UserBean,List<TaskBean>> adminMap = new HashMap<>();
			
			System.out.println("Role "+(role+1));
			String sql = "select * from userr where role_id = " + (role+1);
			adminList= stmt.query(sql,new UserRowMapper());
			for(UserBean admin:adminList)
			{
				taskList = new ArrayList<>();
				String query="select * from task where admin_id="+admin.getUser_id();
				taskList = stmt.query(query,new TaskRowMapper());
				System.out.println("task list "+taskList.toString());
				adminMap.put(admin, taskList);
//				taskList.clear();
			}
			
			System.out.println("task map"+adminMap.toString());
			return adminMap;
		}
	}

	class TaskRowMapper implements RowMapper<TaskBean>{
		@Override
		public TaskBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				TaskBean taskBean = new TaskBean();
				
				taskBean.setUser_id(rs.getInt("user_id"));
				taskBean.setTask_id(rs.getInt("task_id"));
				taskBean.setAdmin_id(rs.getInt("admin_id"));
				taskBean.setComplete(rs.getBoolean("isComplete"));
				taskBean.setCompletion_date(rs.getDate("completion_date"));
				taskBean.setStart_date(rs.getDate("start_date"));
				taskBean.setEnd_date(rs.getDate("end_date"));
				taskBean.setTask_name(rs.getString("task_name"));
				taskBean.setDescription(rs.getString("description"));
				taskBean.setComment(rs.getString("comment"));
				
				if(taskBean.getCompletion_date()!=null)
				{
					days.add(rowNum,taskBean.getCompletion_date().getTime() - taskBean.getEnd_date().getTime());
					days.set(rowNum,days.get(rowNum)/ (1000 * 60 * 60 * 24));	
				}else {
					Date date= new Date();
//						days.add(rowNum,-1000L);
					days.add(rowNum,date.getTime() - taskBean.getEnd_date().getTime());
					days.set(rowNum,days.get(rowNum)/ (1000 * 60 * 60 * 24));
				}
				System.out.println("day   "+days.get(rowNum));
			return taskBean;
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
				userBean.setPassword(rs.getString("password"));
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