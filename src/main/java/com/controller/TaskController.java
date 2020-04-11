package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
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
}
