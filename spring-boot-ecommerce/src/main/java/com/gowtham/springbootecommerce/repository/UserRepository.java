package com.gowtham.springbootecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gowtham.springbootecommerce.model.User;

/**
 * The Interface UserRepository.
 *
 * @author gowthamk
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, String> {

}
