package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.RoleBean;
import com.bean.UserBean;
import com.dao.RoleDao;

@RestController
public class RoleController {

	@Autowired
	RoleDao roleDao;
	
	@PostMapping("/addRole")
	public ResponseBean<Object> addRole(UserBean userBean,String roleName)
	{
		System.out.println(userBean);
		System.out.println(roleName);
		ResponseBean<Object> responseBean = new ResponseBean<>();
		
		boolean status = roleDao.addRoleType(userBean,roleName);
		
		if(status)
		{
			responseBean.setCode(200);
			responseBean.setMessage("successfully added the role "+roleName);
		}
		else
		{
			responseBean.setCode(400);
			responseBean.setMessage("problem occured adding the role "+roleName);
		}
		
		
		return responseBean;
	}
	
	@GetMapping("/listRoles")
	
	public ResponseBean<ArrayList<RoleBean>> getRoles()
	{
		ArrayList<RoleBean> roles = roleDao.listRoles();
		ResponseBean<ArrayList<RoleBean>> responseBean = new ResponseBean<>();
		
		if(roles!=null)
		{	responseBean.setData(roles);
			responseBean.setCode(200);
			responseBean.setMessage(roles.size() + " number of roles");
			
		}
		else
		{
			responseBean.setCode(400);
			responseBean.setMessage("no such record");
			responseBean.setData(null);
		}
		return (ResponseBean<ArrayList<RoleBean>>) responseBean;
	}
}
