package com.ecom.hibernate.modal;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class UserCart implements Serializable{

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long cartId;
	
	@OneToOne
	private EcomUser ecomUser;
	
	@OneToMany(fetch=FetchType.EAGER)
	private Set<Product> product = new HashSet<>();;
	
	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
	public EcomUser getEcomUser() {
		return ecomUser;
	}
	public void setEcomUser(EcomUser ecomUser) {
		this.ecomUser = ecomUser;
	}
	public Set<Product> getProduct() {
		return product;
	}
	public void setProduct(Set<Product> product) {
		this.product = product;
	}
	
	
}
