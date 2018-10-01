package com.ecom.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.ecom.dao.iface.EcomUserDao;
import com.ecom.exception.ApplicationException;
import com.ecom.hibernate.modal.EcomUser;
import com.ecom.hibernate.modal.Order;
import com.ecom.hibernate.modal.OrderProduct;
import com.ecom.hibernate.modal.Product;
import com.ecom.hibernate.modal.UserCart;

@Repository
public class EcomUserDaoImpl implements EcomUserDao {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public boolean createUser(EcomUser user) {
		Session session = sessionFactory.getCurrentSession();
		EcomUser newUser = new EcomUser();
		boolean isUserSaved = false;
		try {
			newUser.setFirstName(user.getFirstName());
			newUser.setLastName(user.getLastName());
			newUser.setUsername(user.getUsername());
			newUser.setPassword(passwordEncoder.encode(user.getPassword()));
			newUser.setEmail(user.getEmail());
			UserCart cart = new UserCart();
			newUser.setUserCart(cart);
			cart.setEcomUser(newUser);
			session.persist(cart);
			session.persist(newUser);
			isUserSaved = true;
		} catch (Exception e) {
			isUserSaved = false;
			throw new ApplicationException("Error in saving user", e);
		}
		return isUserSaved;
	}

	@Override
	public void updatePassword(long id, String password) {
		Session session = sessionFactory.getCurrentSession();
		EcomUser user = session.get(EcomUser.class, id);
		user.setPassword(password);
		session.update(user);
	}

	@Override
	public void addItemToCart(long userId, long productId) {
		Session session = sessionFactory.getCurrentSession();
		EcomUser user = session.get(EcomUser.class, userId);
		Product product = session.get(Product.class, productId);
		user.getUserCart().getProduct().add(product);
		session.update(user);

	}

	@Override
	public long createOrder(long userId, long productId) {
		long orderId = 0;
		Session session = sessionFactory.getCurrentSession();
		try {
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
			session.getTransaction().commit();
			orderId = order.getId();
		} catch(Exception e) {
			throw new ApplicationException("Error while creating order",e);
		}
		return orderId;
	}

	@Override
	public List<Order> getAllOrders(long userId) {
		Session session = sessionFactory.getCurrentSession();
		EcomUser user = session.get(EcomUser.class, userId);
		List<Order> order = new ArrayList<>(user.getOrders());
		return order;
	}

	@Override
	public Order getOrder(long orderId) {
		Session session = sessionFactory.getCurrentSession();
		Order order = session.get(Order.class, orderId);
		return order;
	}

	@Override
	public void cancelOrder(long orderId) {
		Session session = sessionFactory.getCurrentSession();
		Order order = session.get(Order.class, orderId);
		order.setCanceled(true);
		order.setCancelDate(new Date());
		order.setCanceledBy("user");
		session.update(order);
	}

	@Override
	public boolean isUserExists(String username) {
		String hql = "from EcomUser where username=:username and isActive=true";
		int count = 0;
		Session session = sessionFactory.getCurrentSession();
		count = session.createQuery(hql).setParameter("username", username).list().size();
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public EcomUser findByUserName(String username) {
		Session session = sessionFactory.getCurrentSession();
		EcomUser user = session.createQuery("from EcomUser where username=:username", EcomUser.class)
				.setParameter("username", username).getSingleResult();
		return user;
	}

}
