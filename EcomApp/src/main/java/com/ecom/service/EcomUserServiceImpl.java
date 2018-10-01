package com.ecom.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.dao.iface.EcomUserDao;
import com.ecom.hibernate.modal.EcomUser;
import com.ecom.hibernate.modal.Order;
import com.ecom.service.iface.EcomUserService;

@Service
@Transactional
public class EcomUserServiceImpl implements EcomUserService {

	
	EcomUserDao ecomUserDaoImpl;
	
	public EcomUserServiceImpl(EcomUserDao ecomUserDaoImpl) {
		this.ecomUserDaoImpl = ecomUserDaoImpl;
	}

	@Override
	public boolean createUser(EcomUser user) {
		boolean isExist = ecomUserDaoImpl.isUserExists(user.getUsername());
		if (isExist) {
			return true;
		} else {
			ecomUserDaoImpl.createUser(user);
			return false;
		}
	}

	@Override
	public void updatePassword(long id, String password) {
		ecomUserDaoImpl.updatePassword(id, password);

	}

	@Override
	public void addItemToCart(long userId, long productId) {
		ecomUserDaoImpl.addItemToCart(userId, productId);

	}

	@Override
	public long createOrder(long userId, long productId) {
		return ecomUserDaoImpl.createOrder(productId, userId);

	}

	@Override
	public List<Order> getAllOrders(long userId) {
		List<Order> order = ecomUserDaoImpl.getAllOrders(userId);
		return order;
	}

	@Override
	public Order getOrder(long orderId) {
		Order order = ecomUserDaoImpl.getOrder(orderId);
		return order;
	}

	@Override
	public void cancelOrder(long orderId) {
		ecomUserDaoImpl.cancelOrder(orderId);

	}

	@Override
	public EcomUser findByUserName(String username) {
		EcomUser user = ecomUserDaoImpl.findByUserName(username);
		return user;
	}

}
