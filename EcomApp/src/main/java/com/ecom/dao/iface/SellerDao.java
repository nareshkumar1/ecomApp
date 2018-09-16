package com.ecom.dao.iface;

import java.util.List;
import java.util.Map;

import com.ecom.hibernate.modal.Order;
import com.ecom.hibernate.modal.Product;
import com.ecom.hibernate.modal.Seller;

public interface SellerDao {

	public void createSeller(Seller seller);

	public void uploadNewProduct(Map<String, String> product);

	public List<Product> getAllProduct(long sellerId);

	public List<Order> getAllOrders(long sellerId);
	
	public Order getOrder(long orderId);
	
	public Product getProduct(long productId);

	public void deleteProduct(Product product);

	public void updateProduct(Product product);

	public void cancleOrder(long orderId);
	
	public boolean isSellerExists(String username);
}
