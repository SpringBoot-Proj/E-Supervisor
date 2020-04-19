package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.bean.EmailBean;

@Repository
public class EmailDao {
	
	@Autowired
	JdbcTemplate stmt;

	public List<EmailBean> getAll() {
		
		String sql = "select * from mail ";
		
		RowMapper<EmailBean> row = new RowMapper<EmailBean>()
				{

					@Override
					public EmailBean mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						String email = rs.getString("email");
						String message = rs.getString("message");
						return new EmailBean(email,message);
					                                   }	
				             };
	
	
		            return stmt.query(sql, row);
	             }

	public EmailBean  insert(@RequestBody EmailBean mail ) {
		String sql = "insert into mail(email,message) values(?,?)";
		
		 stmt.update(sql,mail.getEmail(),mail.getMessage());
		 
		 return mail;
	}

}


