package com.example.demo.testing.create;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@SpringBootTest
public class CreateProduct {

	@Autowired
	ProductRepository productRepository;
	
	@Test
	public void start() {
		Product p1 = new Product();
		p1.setName("IPHONE13 128GB");
		p1.setCost(12500);
		p1.setPrice(25900);
		
		Product p2 = new Product();
		p2.setName("IPHONE13 Mini 128GB");
		p2.setCost(9000);
		p2.setPrice(21900);
		
		Product p3 = new Product();
		p3.setName("IPHONE13 Pro 128GB");
		p3.setCost(15000);
		p3.setPrice(29900);
		
		productRepository.save(p1);
		productRepository.save(p2);
		productRepository.save(p3);
	}
}
