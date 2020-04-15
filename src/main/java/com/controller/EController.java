package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.EmailBean;
import com.bean.ResponseBean;
import com.dao.EmailDao;

@RestController
public class EController {
	
	
	@Autowired
	EmailDao email;
	
	@PostMapping("/sentMail")
	public ResponseBean<EmailBean> sentEmail(EmailBean emailBean)
	{	 
		ResponseBean<EmailBean> rb = new ResponseBean<EmailBean>();
	   email.insert(emailBean);
	    if(emailBean!=null)
	    {
	       rb.setCode(200);
		   rb.setMessage("Email sent succesfully");
		   rb.setData(emailBean);
		
	    }
	    else
	    {
	    	  rb.setCode(404);
		      rb.setMessage("Email not sent succesfully");
		
	    }
		return rb;

	}
	
	@GetMapping("/emails")
	public  ResponseBean<ArrayList<EmailBean>> getAll()
	{
		ResponseBean<ArrayList<EmailBean>> rb = new ResponseBean<>();
		
		ArrayList<EmailBean> mail = (ArrayList<EmailBean>) email.getAll();
		if(mail!=null)
		{
			   
				rb.setCode(200);
				rb.setMessage("Email list succesfully");
				rb.setData(mail);
		}
		else
		{
			rb.setCode(404);
			rb.setMessage("Email not list succesfully");
		}
		
		return rb;
			
	}

}
