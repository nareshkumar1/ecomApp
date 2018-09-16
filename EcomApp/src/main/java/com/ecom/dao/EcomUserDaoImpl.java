package com.ecom.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.dao.iface.EcomUserDao;
import com.ecom.hibernate.modal.EcomUser;
import com.ecom.hibernate.modal.Order;
import com.ecom.hibernate.modal.OrderProduct;
import com.ecom.hibernate.modal.Product;
import com.ecom.hibernate.modal.UserCart;
import com.ecom.utils.HibernateUtils;

@Repository
public class EcomUserDaoImpl implements EcomUserDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void createUser(EcomUser user) {
		System.out.println("inDao");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		EcomUser newUser = new EcomUser();
		newUser.setFirstName(user.getFirstName());
		System.out.println(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setUsername(user.getUsername());
		newUser.setPassword(user.getPassword());
		newUser.setEmail(user.getEmail());
		UserCart cart = new UserCart();
		newUser.setUserCart(cart);
		cart.setEcomUser(newUser);
		session.persist(cart);
		session.persist(newUser);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void updatePassword(long id, String password) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		EcomUser user = session.get(EcomUser.class, id);
		user.setPassword(password);
		session.update(user);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void addItemToCart(long userId, long productId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		EcomUser user = session.get(EcomUser.class, userId);
		Product product = session.get(Product.class, productId);
		user.getUserCart().getProduct().add(product);
		session.update(user);
		session.getTransaction().commit();
		session.close();

	}

	@Override
	public long createOrder(long userId, long productId) {
		long orderId = 0;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
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
		session.close();
		return orderId;
	}

	@Override
	public List<Order> getAllOrders(long userId) {
		Session session = sessionFactory.openSession();
		EcomUser user = session.get(EcomUser.class, userId);
		List<Order> order = new ArrayList<>(user.getOrders());
		session.close();
		return order;
	}

	@Override
	public Order getOrder(long orderId) {
		Session session = sessionFactory.openSession();
		Order order = session.get(Order.class, orderId);
		session.close();
		return order;
	}

	@Override
	public void cancelOrder(long orderId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Order order = session.get(Order.class, orderId);
		order.setCanceled(true);
		order.setCancelDate(new Date());
		order.setCanceledBy("user");
		session.update(order);
		session.close();
	}

	@Override
	public boolean isUserExists(String username) {
		String hql = "from EcomUser where username=:username and isActive=true";
		int count =0;
		Session session = sessionFactory.openSession();
		count = session.createQuery(hql).setParameter("username", username).list().size();
		session.close();
		if(count>0) {
			return true;
		}else {
			return false;
		}
	}

}
