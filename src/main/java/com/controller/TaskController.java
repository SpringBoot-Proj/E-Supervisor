package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.TaskBean;
import com.bean.UserBean;
import com.dao.TaskDao;

@RestController
public class TaskController {

	@Autowired
	TaskDao taskDao;
	
	@GetMapping("/list_admin")
	public ResponseBean listAdmin()
	{
		List<UserBean> adminList;
		adminList = taskDao.getAdminList();
		ResponseBean<Object> responseBean = new ResponseBean<>();
		if(adminList==null) {
			responseBean.setCode(404);
			responseBean.setData(null);
			responseBean.setMessage("Error:: No admin roles present yet");
		}
		else {
			responseBean.setCode(200);
			responseBean.setData(adminList);
			responseBean.setMessage("Success");
		}
		return responseBean;
	}
	@PostMapping("/add_task")
	public ResponseBean<TaskBean> addTask(TaskBean taskBean)
	{
		taskDao.addTask(taskBean);
		ResponseBean<TaskBean> rb = new ResponseBean<>();
		rb.setMessage("task added");
		rb.setCode(200);
		rb.setData(taskBean);
		
		return rb;
	}
	
	@PostMapping("/update_task/{task_id}")
	public ResponseBean<TaskBean> updateTask(TaskBean taskBean,@PathVariable("task_id") int task_id)
	{
		TaskBean tb=taskDao.updateTask(taskBean,task_id);
		ResponseBean<TaskBean> rb = new ResponseBean<>();
		
		if(tb!=null)
		{
			rb.setCode(200);
			rb.setMessage("Task updated.");
			rb.setData(tb);
		}
		else {
			rb.setCode(400);
			rb.setMessage("No task found");
			rb.setData(null);
		}
		return rb;
	}
	
	@DeleteMapping("delete_task/{task_id}")
	public ResponseBean<TaskBean> deleteTask(@PathVariable("task_id") int task_id)
	{
		boolean b=taskDao.deteleTask(task_id);
		
		ResponseBean rb = new ResponseBean();
		
		if(b)
		{
			rb.setCode(200);
			rb.setMessage("Task deleted.");
			rb.setData(1);
		}
		else {
			rb.setCode(400);
			rb.setMessage("No task found");
			rb.setData(0);
		}
		return rb;
		
	}
	@GetMapping("/list_tasks")
	public ResponseBean<ArrayList<TaskBean>> listTasks() {
		
		ArrayList<TaskBean> tasks = taskDao.listTasks();
		ResponseBean<ArrayList<TaskBean>> rb = new ResponseBean<>();
		
		if(tasks!=null)
		{	rb.setData(tasks);
			rb.setCode(200);
			rb.setMessage(tasks.size()+" tasks found.");
			
		}
		else
		{
			rb.setCode(400);
			rb.setMessage("No tasks found.");
		}
		return rb;
	}
	
	@GetMapping("/view_task/{task_id}")
	public ResponseBean<TaskBean> viewTask(@PathVariable("task_id") int task_id) {
		TaskBean taskBean = taskDao.viewTask(task_id);
		ResponseBean<TaskBean> rb = new ResponseBean<>();
		
		if(taskBean!=null)
		{
			rb.setCode(200);
			rb.setMessage("Task found");
			rb.setData(taskBean);
		}
		else
		{
			rb.setCode(400);
			rb.setMessage("No task found");
		}
		return rb;
		
	}
	
	@PostMapping("/complete_task/{task_id}")
	public ResponseBean<TaskBean> completeTask(@PathVariable("task_id") int task_id)
	{
		TaskBean taskBean=taskDao.completeTask(task_id);
		
		ResponseBean<TaskBean> rb = new ResponseBean<>();
		
		if(taskBean!=null)
		{
			rb.setCode(200);
			rb.setMessage("Task is marked as completed.");
			rb.setData(taskBean);
		}
		else {
			rb.setCode(400);
			rb.setMessage("No task found");
			rb.setData(null);
		}
		return rb;
	}
	
	@PostMapping("/reopen_task/{task_id}")
	public ResponseBean<TaskBean> reopenTask(@PathVariable("task_id") int task_id, TaskBean taskBean)
	{
		ResponseBean<TaskBean> rb = new ResponseBean<>();
		TaskBean tb=taskDao.reopenTask(task_id,taskBean);
		if(tb!=null)
		{
			rb.setCode(200);
			rb.setMessage("Task is reopened and marked as incomplete");
			rb.setData(tb);
		}
		else {
			rb.setCode(400);
			rb.setMessage("No task found");
			rb.setData(null);
		}
		return rb;
	}
}
