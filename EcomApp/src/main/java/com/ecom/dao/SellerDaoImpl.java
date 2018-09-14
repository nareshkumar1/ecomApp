package com.ecom.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ecom.dao.iface.SellerDao;
import com.ecom.hibernate.modal.Order;
import com.ecom.hibernate.modal.Product;
import com.ecom.hibernate.modal.ProductCategory;
import com.ecom.hibernate.modal.Seller;
import com.ecom.utils.HibernateUtils;

@Repository
public class SellerDaoImpl implements SellerDao {

	@Override
	public void createSeller(Seller seller) {
		Session session = HibernateUtils.openSession();
		Seller newSeller = new Seller();
		newSeller.setFirstName(seller.getFirstName());
		newSeller.setLastName(seller.getLastName());
		newSeller.setUsername(seller.getUsername());
		newSeller.setPassword(seller.getPassword());
		newSeller.setEmail(seller.getEmail());
		session.persist(newSeller);
		session.close();

	}

	@Override
	public void uploadNewProduct(long sellerId, Product product) {
		Session session = HibernateUtils.openSession();
		Seller seller = session.get(Seller.class, sellerId);
		Product newProduct = new Product();
		newProduct.setUpcCode(product.getUpcCode());
		newProduct.setProductName(product.getProductName());
		newProduct.setPrice(product.getPrice());
		newProduct.setCost(product.getCost());
		newProduct.setSeller(session.get(Seller.class, sellerId));
		//newProduct.setCategory(session.get(ProductCategory.class, id));
	}

	@Override
	public List<Product> getAllProduct(long sellerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getAllOrders(long sellerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order getOrder(long orderId) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}

}
