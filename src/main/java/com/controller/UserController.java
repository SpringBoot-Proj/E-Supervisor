package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.dao.UserDao;

@RestController
public class UserController {
	@Autowired
	UserDao userDao;
//USER actions(API)s here
}
