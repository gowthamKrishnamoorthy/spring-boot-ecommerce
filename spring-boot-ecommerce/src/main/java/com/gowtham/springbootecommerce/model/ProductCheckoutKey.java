package com.gowtham.springbootecommerce.model;

import java.io.Serializable;

public class ProductCheckoutKey implements Serializable {
	private Integer productId;
 
	private Integer quantity;
 
    // default constructor
 
    public ProductCheckoutKey(Integer productId, Integer quantity) {
    	this.productId = productId;
		this.quantity = quantity;
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

	public ProductCheckoutKey() {
		super();
	}
 
    // equals() and hashCode()
}