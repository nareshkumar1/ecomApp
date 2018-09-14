package com.ecom.dao.iface;

import java.util.List;

import com.ecom.hibernate.modal.EcomUser;
import com.ecom.hibernate.modal.Order;

public interface EcomUserDao {
	
	public void createUser(EcomUser user);
	
	public boolean isUserExists(String username);

	public void updatePassword(long id, String password);

	public void addItemToCart(long userId,long productId);

	public void createOrder(long productId,long userId);
	
	public List<Order> getAllOrders(long userId);
	
	public Order getOrder(long orderId);

	public void cancelOrder(long orderId);
}
