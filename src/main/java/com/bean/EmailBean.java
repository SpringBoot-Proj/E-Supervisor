package com.bean;

import javax.validation.constraints.NotBlank;

public class EmailBean {

	
	@NotBlank(message="please fill the field..")
	private String email,message;


	public EmailBean() {
		
	}

	public EmailBean(@NotBlank(message = "please fill the field..") String email,
			@NotBlank(message = "please fill the field..") String message) {
		super();
		this.email = email;
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
