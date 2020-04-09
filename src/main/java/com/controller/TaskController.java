package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.TaskDao;

@Repository
public class TaskController {
	@Autowired
	TaskDao taskDao;
	// Task APIs here
}
