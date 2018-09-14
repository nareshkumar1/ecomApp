package com.ecom.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ecom.dao.iface.EcomUserDao;
import com.ecom.hibernate.modal.EcomUser;
import com.ecom.hibernate.modal.Order;
import com.ecom.hibernate.modal.OrderProduct;
import com.ecom.hibernate.modal.Product;
import com.ecom.hibernate.modal.UserCart;
import com.ecom.utils.HibernateUtils;

@Repository
public class EcomUserDaoImpl implements EcomUserDao {

	
	@Override
	public void createUser(EcomUser user) {
		Session session = HibernateUtils.openSession();
		EcomUser newUser = new EcomUser();
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setUsername(user.getUsername());
		newUser.setPassword(user.getPassword());
		newUser.setEmail(user.getEmail());
		UserCart cart = new UserCart();
		newUser.setUserCart(cart);
		cart.setEcomUser(newUser);
		session.persist(cart);
		session.persist(newUser);
		session.close();
	}

	@Override
	public void updatePassword(long id, String password) {
		Session session = HibernateUtils.openSession();
		EcomUser user = session.get(EcomUser.class, id);
		user.setPassword(password);
		session.persist(user);
		session.close();
	}

	@Override
	public void addItemToCart(long userId, long productId) {
		Session session = HibernateUtils.openSession();
		EcomUser user = session.get(EcomUser.class, userId);
		Product product = session.get(Product.class, productId);
		user.getUserCart().getProduct().add(product);
		session.close();

	}

	@Override
	public void createOrder(long productId, long userId) {
		Session session = HibernateUtils.openSession();
		EcomUser user = session.get(EcomUser.class, userId);
		Product product = session.get(Product.class, productId);
		Order order = new Order();
		order.setOrderDate(new Date());
		order.setEcomUser(user);
		order.setSeller(product.getSeller());

		user.getOrders().add(order);

		product.getSeller().getOrders().add(order);
		
		OrderProduct orderItem = new OrderProduct();
		orderItem.setOrder(order);
		orderItem.setProduct(product);
		session.persist(order);
		session.persist(user);
		session.persist(product);
		session.persist(orderItem);
		session.close();

	}

	@Override
	public List<Order> getAllOrders(long userId) {
		Session session = HibernateUtils.openSession();
		EcomUser user = session.get(EcomUser.class, userId);
		List<Order> order = new ArrayList<>(user.getOrders());
		session.close();
		return order;
	}

	@Override
	public Order getOrder(long orderId) {
		Session session = HibernateUtils.openSession();
		Order order = session.get(Order.class, orderId);
		session.close();
		return order;
	}

	@Override
	public void cancelOrder(long orderId) {
		Session session = HibernateUtils.openSession();
		Order order = session.get(Order.class, orderId);
		order.setCanceled(true);
		order.setCancelDate(new Date());
		order.setCanceledBy("user");
		session.persist(order);
		session.close();
	}

	@Override
	public boolean isUserExists(String username) {
		String hql = "from EcomUser where username:username and active=true";
		EcomUser user =null;
		Session session = HibernateUtils.openSession();
		user = (EcomUser) session.createQuery(hql).setParameter("username", username).getSingleResult();
		session.close();
		if(user==null) {
			return false;
		}else {
			return true;
		}
	}

}
