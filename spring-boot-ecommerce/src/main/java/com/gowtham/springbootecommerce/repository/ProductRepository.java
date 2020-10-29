package com.gowtham.springbootecommerce.repository;

import java.util.List;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gowtham.springbootecommerce.model.Category;
import com.gowtham.springbootecommerce.model.Product;

/**
 * The Interface ProductRepository.
 *
 * @author gowthamk
 * @version 1.0
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {
	List<Product>findByCategory(Category category,Sort sort);
}
