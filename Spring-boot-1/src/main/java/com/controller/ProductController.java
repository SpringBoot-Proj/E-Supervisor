package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ProductBean;
import com.bean.ResponseBean;
import com.dao.ProductDao;

@RestController
public class ProductController {
	
	@Autowired
	ProductDao productDao;
	
	@PostMapping("/product")
	public ResponseBean<ProductBean> addProduct(ProductBean productBean) {
		productDao.addProduct(productBean);
		ResponseBean<ProductBean> rb = new ResponseBean<ProductBean>();
		rb.setCode(200);
		rb.setMessage("Product added");
		rb.setData(productBean);
		return rb;
	}
	 
	@GetMapping("/product/{id}")
	public ProductBean getProductById(@PathVariable int id) {
		ProductBean productBean = productDao.getProductById(id);
		return productBean;
	}
	
	@GetMapping("/products")
	public List<ProductBean> getAllProducts(){
		List<ProductBean> products = productDao.getAllProducts();
		return products;
		
	}
	
	@DeleteMapping("/product/{id}")
	public String deleteProduct(@PathVariable int id) {
		boolean flag = productDao.deleteProduct(id);
		if(flag) {
			return "Product was removed";
		}else {
			return "Something went wrong";
		}
		
	}
	
	@PutMapping("/product")
	public ProductBean updateProduct(ProductBean productBean) {
		productDao.updateroduct(productBean);
		return productBean;
	}

}
