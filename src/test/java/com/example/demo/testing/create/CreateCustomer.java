package com.example.demo.testing.create;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;

@SpringBootTest
public class CreateCustomer {

	@Autowired
	CustomerRepository customerRepository;
	
	@Test
	public void start() {
		Customer c1 = new Customer();
		c1.setName("Taiwan Mobile");
		
		Customer c2 = new Customer();
		c2.setName("CHT");
		
		customerRepository.save(c1);
		customerRepository.save(c2);
	}
}
