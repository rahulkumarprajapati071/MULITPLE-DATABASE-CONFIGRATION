package in.glootech.second.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import in.glootech.second.entity.Product;

public interface ProductDao extends JpaRepository<Product, Integer>{

}
