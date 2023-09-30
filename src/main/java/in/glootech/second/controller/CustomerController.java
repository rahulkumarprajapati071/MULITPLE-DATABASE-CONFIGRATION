package in.glootech.second.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.glootech.dao.CustomerDao;
import in.glootech.entity.Customer;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	private CustomerDao customerDao;
	
	public CustomerController(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	@PostMapping("/add")
	public String addCustomer(@RequestBody Customer customer) {
		customerDao.save(customer);
		
		return "Customer added succesfully";
	}
	
	@GetMapping("/get/{id}")
	public Optional<Customer> getProduct(@PathVariable Integer id) {
		return customerDao.findById(id);
	}
	
	@DeleteMapping("/remove/{id}")
	public String removeProduct(@PathVariable Integer id) {
		customerDao.deleteById(id);
		return "Remove Successful";
	}

}
