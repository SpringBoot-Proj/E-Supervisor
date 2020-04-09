package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.UserBean;
import com.dao.UserDao;

@RestController
public class UserController {

	@Autowired
	UserDao userDao;
	
	@PostMapping("/adduser")
	public UserBean add_user(UserBean userBean)
	{
		userDao.add_user(userBean);
		return userBean;
	}
	
	@PutMapping("/updatepro")
	public UserBean update_profile(UserBean userBean)
	{
		userDao.update_profile(userBean);
		return userBean;
	}
	
}
