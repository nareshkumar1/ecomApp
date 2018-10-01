package com.ecom.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.ecom.dao.iface.SellerDao;
import com.ecom.exception.ApplicationException;
import com.ecom.hibernate.modal.Order;
import com.ecom.hibernate.modal.Product;
import com.ecom.hibernate.modal.ProductCategory;
import com.ecom.hibernate.modal.Seller;

@Repository
public class SellerDaoImpl implements SellerDao {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void createSeller(Seller seller) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Seller newSeller = new Seller();
			newSeller.setFirstName(seller.getFirstName());
			newSeller.setLastName(seller.getLastName());
			newSeller.setUsername(seller.getUsername());
			newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
			newSeller.setEmail(seller.getEmail());
			session.persist(newSeller);
		}catch(Exception e) {
			throw new ApplicationException("Error creating seller",e);
		}
		
	}

	@Override
	public void uploadNewProduct(Map<String, String> product) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Seller seller = session.get(Seller.class, product.get("sellerId"));
			Product newProduct = new Product();
			newProduct.setUpcCode(product.get("upcCode"));
			newProduct.setProductName(product.get("productName"));
			newProduct.setPrice(Double.parseDouble(product.get("price")));
			newProduct.setCost(Double.parseDouble(product.get("cost")));
			newProduct.setQty(Integer.parseInt(product.get("qty")));
			newProduct.setSeller(seller);
			newProduct.setCategory(session.get(ProductCategory.class, product.get("categroyId")));
			session.persist(newProduct);
		} catch(Exception e) {
			throw new ApplicationException("Error uploading product",e);
		}
		
	}

	@Override
	public List<Product> getAllProduct(long sellerId) {
		List<Product> products=null;
		Session session = sessionFactory.getCurrentSession();
		products = session.createQuery("from Product where seller.sellerId=:sellerId",Product.class).setParameter("sellerId", sellerId).list();
		return products;
	}

	@Override
	public List<Order> getAllOrders(long sellerId) {
		List<Order> orders = null;
		Session session = sessionFactory.getCurrentSession();
		orders = session.createQuery("from Order where seller.sellerId=:sellerId",Order.class).setParameter("sellerId", sellerId).list();
		return orders;
	}

	@Override
	public Order getOrder(long orderId) {
		Session session = sessionFactory.getCurrentSession();
		Order order = session.get(Order.class, orderId);
		return order;
	}

	@Override
	public Product getProduct(long productId) {
		Session session =  sessionFactory.getCurrentSession();
		Product product = session.get(Product.class, productId);
		return product;
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancleOrder(long orderId) {
		Session session = sessionFactory.getCurrentSession();
		Order order = session.get(Order.class, orderId);
		order.setCancelDate(new Date());
		order.setCanceled(true);
		order.setCanceledBy("Seller");
		//order.setCancelReason(cancelReason);
		session.update(order);

	}
	
	@Override
	public boolean isSellerExists(String username) {
		String hql = "from Seller where username=:username and isActive=true";
		int count=0;
		Session session = sessionFactory.getCurrentSession();
		count = session.createQuery(hql).setParameter("username", username).list().size();
		if(count>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Seller findByUserName(String username) {
		Session session = sessionFactory.getCurrentSession();
		Seller seller = session.createQuery("from Seller where username=:username",Seller.class).setParameter("username", username).getSingleResult();
		return seller;
	}
}
