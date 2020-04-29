package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Date;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

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
	boolean isOverPerf = false;
	ArrayList<Double> days=new ArrayList<>();
	public List<TaskBean> getUnderPerfTasks() {
		isOverPerf=false;
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
                return Double.compare(arg0.getValue(), arg1.getValue());
            }
        });
		List<TaskBean> taskList2 = new ArrayList<>();
		for(KeyValue kv:myList)
		{
			taskList2.add(taskList.get(kv.getKey()));
		}
		return taskList2;
	}
	public List<TaskBean> getOverPerfTasks() {
		days.clear();
		isOverPerf=true;
		List<TaskBean> taskList = new ArrayList<>();
		String query="select * from task where completion_date is not null and end_date>=completion_date";
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
                return Double.compare(arg0.getValue(), arg1.getValue());
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
	    private Double value;

	    public KeyValue(int i, Double j) {
	        key  = i;
	        value = j;
	    }
	    public int getKey() {
	        return key;
	    }
	    public void setKey(int key) {
	        this.key = key;
	    }

	    public Double getValue() {
	        return value;
	    }

	    public void setValue(Double value) {
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
				taskBean.setIsComplete(rs.getInt("iscomplete"));
				taskBean.setCompletion_date(rs.getDate("completion_date"));
				taskBean.setStart_date(rs.getDate("start_date"));
				taskBean.setEnd_date(rs.getDate("end_date"));
				taskBean.setTask_name(rs.getString("task_name"));
				taskBean.setDescription(rs.getString("description"));
				taskBean.setComment(rs.getString("comment"));
				
				if(taskBean.getCompletion_date()!=null)
				{
					if(isOverPerf==true)
					{
						long totalDiff = taskBean.getEnd_date().getTime()-taskBean.getStart_date().getTime();
						long remainingDiff = taskBean.getEnd_date().getTime()-taskBean.getCompletion_date().getTime();
						double per = remainingDiff * 1.0 /totalDiff;
						System.out.println("percent "+rowNum+": "+totalDiff);
						days.add(rowNum,per);
					}else {
					days.add(rowNum,(1.0)*taskBean.getCompletion_date().getTime() - taskBean.getEnd_date().getTime());
					days.set(rowNum,days.get(rowNum)/ (1000 * 60 * 60 * 24));
					}
				}else {
					Date date= new Date();
					days.add(rowNum,(1.0)*date.getTime() - taskBean.getEnd_date().getTime());
					days.set(rowNum,-days.get(rowNum)/ (1000 * 60 * 60 * 24));
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
		System.out.println("Role 2 "+roleBean.getRole_name());
		return roleBean!=null?roleBean.getRole_id():-1;
	}


	public void addTask(TaskBean taskBean) {
		
		stmt.update("insert into task (task_name,description,start_date,end_date,comment,user_id,admin_id) values (?,?,?,?,?,?)",taskBean.getTask_name(),taskBean.getDescription(),taskBean.getStart_date(),taskBean.getEnd_date(),taskBean.getComment(),taskBean.getUser_id(),taskBean.getAdmin_id());
		
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
			bean.setStart_date(rs.getDate("start_date"));
			bean.setEnd_date(rs.getDate("end_date"));
			bean.setIsComplete(rs.getInt("iscomplete"));
			bean.setCompletion_date(rs.getDate("completion_date"));
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
		
		//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		//Date date = new Date();
		//String date_time = formatter.format(date);
		//System.out.println(date_time);
		LocalDate currentDate = LocalDate.now();
		int i=stmt.update("update task set iscomplete=1,comment='',completion_date=? where task_id=?",currentDate,task_id);
		if(i==1)
			return viewTask(task_id);
		else
			return null;
	}

	public TaskBean reopenTask(int task_id, TaskBean taskBean) {
		
		int i=stmt.update("update task set iscomplete=0, completion_date=null, comment=? where task_id=? ",taskBean.getComment(),task_id);
		if(i==1)
			return viewTask(task_id);
		else
			return null;
	}


}
