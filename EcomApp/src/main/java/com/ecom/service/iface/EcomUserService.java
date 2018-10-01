package com.ecom.service.iface;

import java.util.List;

import com.ecom.hibernate.modal.EcomUser;
import com.ecom.hibernate.modal.Order;

public interface EcomUserService {

	public boolean createUser(EcomUser user);

	public void updatePassword(long id, String password);

	public void addItemToCart(long userId, long productId);

	public long createOrder(long userId, long productId);

	public List<Order> getAllOrders(long userId);

	public Order getOrder(long orderId);

	public void cancelOrder(long orderId);
	
	public EcomUser findByUserName(String username);

}
