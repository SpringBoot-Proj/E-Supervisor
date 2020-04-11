package com.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;

import com.bean.UserDataBean;
import com.dao.UserDao;

@RestController
public class UserController {
	@Autowired
	UserDao userDao;

	@Autowired
    private JavaMailSender mailSender;
	
	@PostMapping("/login")
	public ResponseBean<Object> validateUser(@RequestParam("email") String email,@RequestParam("password") String pass,HttpSession session){
		
		ResponseBean<Object> responseBean = new ResponseBean<>();
		UserDataBean userDataBean =userDao.getUserDataByEmail(email);
		
		if(userDataBean.getPassword().equals(pass)) {
			
			session.setAttribute("user_id",userDataBean.getUser_id());
			session.setAttribute("first_name",userDataBean.getFirst_name());
			session.setAttribute("last_name",userDataBean.getLast_name());
			session.setAttribute("email",userDataBean.getEmail());
			session.setAttribute("role_id",userDataBean.getRole_id());
			
			userDataBean.setPassword(null);
			responseBean.setData(userDataBean);
			responseBean.setCode(1);
			responseBean.setMessage("User is Valid");
			
		}else {
			
			responseBean.setCode(0);
			responseBean.setMessage("User is  Not Valid");
		
		}
		
		return responseBean;
	}
	
	@GetMapping("/logout")
	public ResponseBean<Object> logoutUser(HttpSession session){
		ResponseBean<Object> responseBean = new ResponseBean<>();
		
			session.removeAttribute("user_id");
			session.removeAttribute("first_name");
			session.removeAttribute("last_name");
			session.removeAttribute("email");
			session.removeAttribute("role_id");
			
			responseBean.setCode(1);
			responseBean.setMessage("Logout Successful..");			
		
		return responseBean;
	}
	
	@PostMapping("/resetpass")
	public ResponseBean<Object> resetPassword(@RequestParam("email") String email) throws AddressException{
		
			ResponseBean<Object> responseBean = new ResponseBean<>();
			UserDataBean userDataBean = userDao.getUserDataByEmail(email);
		    MimeMessage mimeMessage = mailSender.createMimeMessage();
		    
			 int code=0;
		     try {
		        	
		            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		            mimeMessageHelper.setSubject("Reset Pass");
		            mimeMessageHelper.setFrom(new InternetAddress(email, "eSupervise.com"));
		            mimeMessageHelper.setTo("spemeet999@gmail.com");
		            mimeMessageHelper.setText("pass is: " +userDataBean.getPassword());
		 
		            mailSender.send(mimeMessageHelper.getMimeMessage());
		            code= 1;
		        } catch (MessagingException e) {
		            e.printStackTrace();
		        } catch (UnsupportedEncodingException e) {
		            e.printStackTrace();
		        }
		     
			responseBean.setCode(code);
			
			if(code==0) {
				responseBean.setMessage("Something went wrong...Please try after Some times");
					
			}else {
			responseBean.setMessage("Password is Sent to your Email  Successfully..");
			}
			
		return responseBean;
	}
	
}