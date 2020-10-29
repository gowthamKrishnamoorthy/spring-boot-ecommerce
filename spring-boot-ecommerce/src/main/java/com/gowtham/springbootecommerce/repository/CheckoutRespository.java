package com.gowtham.springbootecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gowtham.springbootecommerce.model.CheckOut;

/**
 * The Interface UserRepository.
 *
 * @author gowthamk
 * @version 1.0
 */
public interface CheckoutRespository extends JpaRepository<CheckOut, String> {
	List<CheckOut> findByUsername(String userName);

	void deleteByUsername(String user);

}
