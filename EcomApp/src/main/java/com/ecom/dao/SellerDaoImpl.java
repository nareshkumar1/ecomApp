package com.ecom.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecom.dao.iface.SellerDao;
import com.ecom.hibernate.modal.EcomUser;
import com.ecom.hibernate.modal.Order;
import com.ecom.hibernate.modal.Product;
import com.ecom.hibernate.modal.ProductCategory;
import com.ecom.hibernate.modal.Seller;
import com.ecom.utils.HibernateUtils;

@Repository
public class SellerDaoImpl implements SellerDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void createSeller(Seller seller) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Seller newSeller = new Seller();
		newSeller.setFirstName(seller.getFirstName());
		newSeller.setLastName(seller.getLastName());
		newSeller.setUsername(seller.getUsername());
		newSeller.setPassword(seller.getPassword());
		newSeller.setEmail(seller.getEmail());
		session.persist(newSeller);
		session.getTransaction().commit();
		session.close();

	}

	@Override
	public void uploadNewProduct(Map<String, String> product) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
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
		
		session.close();
	}

	@Override
	public List<Product> getAllProduct(long sellerId) {
		List<Product> products=null;
		Session session = sessionFactory.openSession();
		products = session.createQuery("from Product where seller.sellerId=:sellerId",Product.class).setParameter("sellerId", sellerId).list();
		session.close();
		return products;
	}

	@Override
	public List<Order> getAllOrders(long sellerId) {
		List<Order> orders = null;
		Session session = sessionFactory.openSession();
		orders = session.createQuery("from Order where seller.sellerId=:sellerId",Order.class).setParameter("sellerId", sellerId).list();
		session.close();
		return orders;
	}

	@Override
	public Order getOrder(long orderId) {
		Session session = sessionFactory.openSession();
		Order order = session.get(Order.class, orderId);
		session.close();
		return order;
	}

	@Override
	public Product getProduct(long productId) {
		// TODO Auto-generated method stub
		return null;
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
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Order order = session.get(Order.class, orderId);
		order.setCancelDate(new Date());
		order.setCanceled(true);
		order.setCanceledBy("Seller");
		//order.setCancelReason(cancelReason);
		session.update(order);
		session.getTransaction().commit();
		session.close();

	}
	
	@Override
	public boolean isSellerExists(String username) {
		String hql = "from Seller where username=:username and active=true";
		int count=0;
		Session session = HibernateUtils.openSession();
		count = session.createQuery(hql).setParameter("username", username).list().size();
		session.close();
		if(count>0) {
			return true;
		}else {
			return false;
		}
	}

}
