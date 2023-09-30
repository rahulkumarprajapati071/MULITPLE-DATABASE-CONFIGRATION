package in.glootech.controller;

import java.util.Optional;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.glootech.second.dao.ProductDao;
import in.glootech.second.entity.Product;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	private ProductDao productDao;
	
	public ProductController(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	@PostMapping("/add")
	public String addProduct(@RequestBody Product product) {
		productDao.save(product);
		
		return "Product Added Successfull";
	}
	
	@GetMapping("/get/{id}")
	public Optional<Product> getProduct(@PathVariable Integer id) {
		return productDao.findById(id);
	}
	
	@DeleteMapping("/remove/{id}")
	public String removeProduct(@PathVariable Integer id) {
		productDao.deleteById(id);
		return "Remove Successful";
	}
}
