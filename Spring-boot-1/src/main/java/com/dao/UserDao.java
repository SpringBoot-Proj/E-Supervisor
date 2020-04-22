package com.dao;

import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.bean.UserBean;

@Repository
public class UserDao{
	
	ArrayList<UserBean> users = new ArrayList<UserBean>();
	
	public void insertUser(UserBean userBean) {
		users.add(userBean);
	}

	public ArrayList<UserBean> listUsers() {
		// TODO Auto-generated method stub
		return users;
	}
	
	public UserBean getUserByEmail(String email){
		for(UserBean u : users) {
			if(u.getEmail().equals(email)) {
				return u;
			}
		}
		return null;
	}
	
	public void deleteUser(String email) {
		for(UserBean u : users) {
			if(u.getEmail().equals(email)) {
				users.remove(u);
				break;
			}
		}
	}


	public ArrayList<UserBean> updateUser(UserBean userBean, String email) {

			for(int i=0; i<users.size(); i++) {
				UserBean u = users.get(i);
				if(u.getEmail().equals(email)) {
					users.set(i,userBean);			
					}
			}
			return users;
		}
	}
