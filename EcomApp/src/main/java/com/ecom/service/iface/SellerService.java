package com.ecom.service.iface;

import java.util.List;
import java.util.Map;

import com.ecom.hibernate.modal.Order;
import com.ecom.hibernate.modal.Product;
import com.ecom.hibernate.modal.Seller;

public interface SellerService {

	public boolean createSeller(Seller seller);

	public void uploadNewProduct(Map<String, String> product);

	public List<Product> getAllProduct(long sellerId);

	public List<Order> getAllOrders(long sellerId);

	public Order getOrder(long orderId);

	public Product getProduct(long productId);

	public void deleteProduct(Product product);

	public void updateProduct(Product product);

	public void cancleOrder(long orderId);
}
