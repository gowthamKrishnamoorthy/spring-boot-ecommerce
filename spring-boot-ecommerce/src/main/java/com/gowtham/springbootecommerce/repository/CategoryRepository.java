package com.gowtham.springbootecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gowtham.springbootecommerce.model.Category;

/**
 * The Interface CategoryRepository.
 *
 * @author gowthamk
 * @version 1.0
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
