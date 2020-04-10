package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseBean<Object> addRole(int userid, String roleName) {
		ResponseBean<Object> responseBean = new ResponseBean<>();

		int roleid = roleDao.addRoleType(userid, roleName);
		RoleBean roleBean = new RoleBean();
		roleBean.setRoleID(roleid);
		roleBean.setRoleName(roleName);

		if (roleid > 0) {
			responseBean.setCode(200);
			responseBean.setMessage("successfully added the role for userid=>" + userid);
			responseBean.setData(roleBean);
		} else {
			responseBean.setCode(400);
			responseBean.setMessage("problem occured adding the role for userid=>" + userid);
			responseBean.setData(roleBean);
		}

		return responseBean;
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

	@PutMapping("/updateRole")
	public ResponseBean<RoleBean> updateRole(int userid, String roleName) {
		ResponseBean<RoleBean> responseBean = new ResponseBean<>();
		int roleid = roleDao.addRoleType(userid, roleName);
		RoleBean roleBean = new RoleBean();
		roleBean.setRoleID(roleid);
		roleBean.setRoleName(roleName);

		if (roleid > 0) {
			responseBean.setCode(200);
			responseBean.setMessage("successfully updated the role for userid=>"+userid);
			responseBean.setData(roleBean);
		} else {
			responseBean.setCode(400);
			responseBean.setMessage("problem occured updating the role for userid=>"+userid);
			responseBean.setData(roleBean);
		}

		return responseBean;
	}

}
