package com.gowtham.springbootecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * The checkout class.
 *
 * @author gowthamk
 * @version 1.0
 */
@Entity
@IdClass(ProductCheckoutKey.class)
public class CheckOut {

	@Id
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	
	@Id
	@Column(name = "productId", unique = true, nullable = false)
	private Integer productId;

	@Column(name = "quantity",  nullable = false)
	private Integer quantity;

	public CheckOut() {
	}
	
	public CheckOut(String username, Integer productId, Integer quantity) {
		this.username = username;
		this.productId = productId;
		this.quantity = quantity;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
}
