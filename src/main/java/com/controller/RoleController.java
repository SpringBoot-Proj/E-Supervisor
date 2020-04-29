package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.RoleBean;
import com.bean.TaskBean;
import com.dao.RoleDao;

@RestController
public class RoleController {

	@Autowired
	RoleDao roleDao;

	@PostMapping("/addRole")
	public ResponseBean<RoleBean> addRole(RoleBean roleBean) {
		ResponseBean<Object> responseBean = new ResponseBean<>();

	
		roleDao.addRoleType(roleBean);
		ResponseBean<RoleBean> rb = new ResponseBean<RoleBean>();
		
		rb.setMessage("Role added");
		rb.setCode(200);
		rb.setData(roleBean);
		

//		if (roleid > 0) {
//			responseBean.setCode(200);
//			responseBean.setMessage("successfully added the role for userid=>" + role_id);
//			responseBean.setData(roleBean);
//		} else {
//			responseBean.setCode(400);
//			responseBean.setMessage("problem occured adding the role for userid=>" + role_id);
//			responseBean.setData(roleBean);
//		}

		return rb;
	}

	@GetMapping("/listRoles")
	public ResponseBean<ArrayList<RoleBean>> listRoles() {
		ArrayList<RoleBean> roles = roleDao.listRoles();
		ResponseBean<ArrayList<RoleBean>> responseBean = new ResponseBean<>();

		if (roles != null) {
			responseBean.setData(roles);
			responseBean.setCode(200);
			responseBean.setMessage(roles.size() + " number of roles");

		} else {
			responseBean.setCode(400);
			responseBean.setMessage("no such record");
			responseBean.setData(null);
		}
		return (ResponseBean<ArrayList<RoleBean>>) responseBean;
	}

	@PutMapping("/updateRole/{role_id}")
	public ResponseBean<RoleBean> updateRole(RoleBean roleBean,@PathVariable("role_id") int role_id) {
		
		ResponseBean<RoleBean> rb = new ResponseBean<>();
		RoleBean rb1= roleDao.updateRoleType(roleBean,role_id);
		
		if(rb1!=null)
		{
			rb.setCode(200);
			rb.setMessage("Role updated.");
			rb.setData(rb1);;
		}
		else {
			rb.setCode(400);
			rb.setMessage("No Role found");
			rb.setData(null);
		}
		return rb;
	}
	@DeleteMapping("delete_role/{role_id}")
	public ResponseBean<TaskBean> deleteRole(@PathVariable("role_id") int role_id)
	{
		boolean b= roleDao.deteleRole(role_id);
		
		ResponseBean rb = new ResponseBean();
		
		if(b)
		{
			rb.setCode(200);
			rb.setMessage("Role deleted.");
			rb.setData(1);
		}
		else {
			rb.setCode(400);
			rb.setMessage("No Role found");
			rb.setData(0);
		}
		return rb;
		
	}
}
