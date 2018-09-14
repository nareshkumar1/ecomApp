package com.ecom.hibernate.modal;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class EcomUser implements Serializable {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private boolean isActive=true;
	private boolean isRetailer=false;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="ecomUser")
	private Set<Order> orders;

	@OneToOne
	private UserCart userCart;
	
	
	
	public UserCart getUserCart() {
		return userCart;
	}

	public void setUserCart(UserCart userCart) {
		this.userCart = userCart;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	
}
