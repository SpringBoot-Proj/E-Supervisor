package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.bean.ProductBean;

@Repository	

public class ProductDao {

	@Autowired
	JdbcTemplate stmt;
//	@Autowired
//	private NamedParameterJdbcTemplate stmt;
	
	
	public void addProduct(ProductBean productBean) {
		// TODO Auto-generated method stub
		stmt.update("insert into products (name,price) values (?,?)",productBean.getName(),productBean.getPrice());
	}

	public ProductBean getProductById(int id) {
		// TODO Auto-generated method stub
		ProductBean productBean = null;
		productBean = stmt.queryForObject("select * from products where id="+id, new BeanPropertyRowMapper<ProductBean>(ProductBean.class));
		return productBean;
	}

	public List<ProductBean> getAllProducts() {
		List<ProductBean> products = new ArrayList<>();

		products = stmt.query("select * from users",new ProductRowMapper());

		return products;
	}

	class ProductRowMapper implements org.springframework.jdbc.core.RowMapper<ProductBean> {

		@Override
		public ProductBean mapRow(ResultSet rs, int rowNum) throws SQLException {

			ProductBean productBean = new ProductBean();
			productBean.setId(rs.getInt("id"));
			productBean.setName(rs.getString("name"));
			productBean.setPrice(rs.getInt("price"));

			return productBean;
		}

	}

	public boolean deleteProduct(int id) {
		// TODO Auto-generated method stub
		int i = stmt.update("delete from products where id="+id);
		if(i==0)
			return false;
		else
			return true;
	}

	public boolean updateroduct(ProductBean productBean) {
		// TODO Auto-generated method stub
		int i = stmt.update("update product set name=?, price=?, where id=?",productBean.getName(),productBean.getPrice(),productBean.getId());
		if(i==1) {
			return true;
		}else {
			return false;
		}
	}

}
	