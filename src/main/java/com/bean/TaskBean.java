package com.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class TaskBean {

	private int task_id;
	private int user_id;
	private int admin_id;
	private String task_name;
	
	private String description;
	
	@DateTimeFormat(pattern="DD-MM-YYYY")
	private Date start_date;
	@DateTimeFormat(pattern="DD-MM-YYYY")
	private Date end_date;
	@DateTimeFormat(pattern="DD-MM-YYYY")
	private Date completion_date;
	private int isComplete;
	public int getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(int isComplete) {
		this.isComplete = isComplete;
	}
	private String comment;
	
	
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public Date getCompletion_date() {
		return completion_date;
	}
	public void setCompletion_date(Date completion_date) {
		this.completion_date = completion_date;
	}
	public int getTask_id() {
		return task_id;
	}
	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
