package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.UserBean;
import com.dao.UserDao;

@RestController
public class UserController {

	@Autowired
	UserDao userDao;
	
	@PostMapping("/adduser")
	public ResponseBean<UserBean> add_user(UserBean userBean,String roleName)
	{
		int userid = userDao.add_user(userBean);
	
		ResponseBean<UserBean> responseBean = new ResponseBean<>();
		responseBean.setMessage("user added");
		responseBean.setCode(200);
		return responseBean;
	
	}
	
	@PutMapping("/updatepro")
	public UserBean update_profile(UserBean userBean)
	{
		userDao.update_profile(userBean);
		return userBean;
	}
	
}
