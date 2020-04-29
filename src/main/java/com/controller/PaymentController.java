package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.service.PaymentService;

@Controller
public class PaymentController {

	@Autowired
	PaymentService paymentService;
	
	@GetMapping("/checkout")
	public String checkout() {
		return "Checkout";
	}
	
	@PostMapping("/paynow")
	public String paynow(@RequestParam("amount") int amount,@RequestParam("cc") String cc,@RequestParam("dt") String dt) {
		paymentService.ChargeCreditCard(amount);
		return "PaymentStatus";
	}
}
