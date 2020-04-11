package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.RoleBean;
import com.bean.UserBean;
import com.dao.UserDao;

/*
add_user --> Aastha
change_password --> Aastha
update_profile --> Aastha
delete_user --> Pheni
list_users --> Pheni
view_user --> Pheni
* */

@RestController
public class UserController {

	@Autowired
	UserDao userDao;
	
	//delete_user
	@DeleteMapping("user/{user_id}")
	public ResponseBean<Integer> deleteUser(@PathVariable("user_id") int id){
		 
		int rowsAffected= userDao.deleteUser(id);
		ResponseBean rb = new ResponseBean();
		
		if(rowsAffected==1)
		{
			rb.setCode(200);
			rb.setMessage("User deleted.");
			rb.setData(1);
		}
		else {
			rb.setCode(400);
			rb.setMessage("User not deleted.");
			rb.setData(0);
		}
		return rb;
	}
	
	//list_users
	@GetMapping("/users")
	public ResponseBean<ArrayList<UserBean>> listUsers() {
		ArrayList<UserBean> users = userDao.listUsers();
		ResponseBean<ArrayList<UserBean>> rb = new ResponseBean<>();
		
		if(users!=null)
		{	rb.setData(users);
			rb.setCode(200);
			rb.setMessage(users.size()+" users found.");
			
		}
		else
		{
			rb.setCode(400);
			rb.setMessage("No users found.");
		}
		return rb;
	}
	
	//view_user
	@GetMapping("/user/{user_id}")
	public ResponseBean<UserBean> getUser(@PathVariable("user_id") int id) {
		UserBean userBean = userDao.getUserByFirstName(id);
		ResponseBean rb = new ResponseBean();
		if(userBean!=null)
		{
			rb.setCode(200);
			rb.setMessage("User found");
			rb.setData(userBean);
		}
		else
		{
			rb.setCode(400);
			rb.setMessage("No user found");
		}
		return rb;
		
	}
}
