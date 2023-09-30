package in.glootech.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import in.glootech.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer>{

}
