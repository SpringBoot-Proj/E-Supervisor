package com.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseBean<Object> listAdmin()
	{
		HashMap<UserBean,List<TaskBean>> adminMap;
		adminMap= taskDao.getAdminList();
		ResponseBean<Object> responseBean = new ResponseBean<>();
		if(adminMap==null) {
			responseBean.setCode(404);
			responseBean.setData(null);
			responseBean.setMessage("Error:: No admin roles present yet");
		}
		else {
			responseBean.setCode(200);
			responseBean.setData(adminMap);
			responseBean.setMessage("Success");
		}
		return responseBean;
	}
	
	@GetMapping("/list_underperformed_tasks")
	public ResponseBean<Object> getUnderPerformedTasks()
	{
		ResponseBean<Object> responseBean = new ResponseBean<>();
		List<TaskBean> taskList;
		taskList = taskDao.getUnderPerfTasks();
		if(taskList==null) {
			responseBean.setCode(404);
			responseBean.setData(null);
			responseBean.setMessage("Error:: No under performed tasks present yet");
		}
		else {
			responseBean.setCode(200);
			responseBean.setData(taskList);
			responseBean.setMessage("Success");
		}
		return responseBean;
	}
	
	@GetMapping("/list_overperformed_tasks")
	public ResponseBean<Object> getOverPerformedTasks()
	{
		ResponseBean<Object> responseBean = new ResponseBean<>();
		List<TaskBean> taskList;
		taskList = taskDao.getOverPerfTasks();
		if(taskList==null) {
			responseBean.setCode(404);
			responseBean.setData(null);
			responseBean.setMessage("Error:: No over performed tasks present yet");
		}
		else {
			responseBean.setCode(200);
			responseBean.setData(taskList);
			responseBean.setMessage("Success");
		}
		return responseBean;
	}
}
