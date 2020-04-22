package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.UserBean;
import com.dao.UserDao;

@RestController
public class UserController {

	@Autowired
	UserDao userDao;
	
	@PostMapping("/user")
	public ResponseBean<UserBean> addUser(UserBean userBean) {
		userDao.insertUser(userBean);
		ResponseBean<UserBean> rb = new ResponseBean<UserBean>();
		rb.setCode(200);
		rb.setData(userBean);
		rb.setMessage("User Added");
		return rb;
	}
	
	@GetMapping("/users")
	public ArrayList<UserBean> listUsers(){
		ArrayList<UserBean> userBean = userDao.listUsers();
		return userBean;
	}
	
	@GetMapping("/user/{email}")
	public UserBean getUser(@PathVariable("email") String email){
		UserBean userBean = userDao.getUserByEmail(email);
		return userBean;
	}

	@DeleteMapping("/user/{email}")
	public ArrayList<UserBean> deleteUser(@PathVariable("email") String email){
		userDao.deleteUser(email);
		return userDao.listUsers();
	}
	
	@PutMapping("/user/{email}")
	public ArrayList<UserBean> updateUser(@RequestBody UserBean userBean,@PathVariable("email") String email) {
		
		userDao.updateUser(userBean,email);
		userBean.setEmail(email);
		return userDao.listUsers();
			
	
		
	}
	
}
