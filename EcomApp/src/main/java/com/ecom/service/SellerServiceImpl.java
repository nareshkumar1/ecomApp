package com.ecom.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.dao.iface.SellerDao;
import com.ecom.hibernate.modal.Order;
import com.ecom.hibernate.modal.Product;
import com.ecom.hibernate.modal.Seller;
import com.ecom.service.iface.SellerService;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

	@Autowired
	SellerDao sellerDaoImpl;

	@Override
	public boolean createSeller(Seller seller) {
		boolean isExists = sellerDaoImpl.isSellerExists(seller.getUsername());
		if (isExists) {
			return true;
		} else {
			sellerDaoImpl.createSeller(seller);
			return false;
		}

	}

	@Override
	public void uploadNewProduct(Map<String, String> product) {
		sellerDaoImpl.uploadNewProduct(product);

	}

	@Override
	public List<Product> getAllProduct(long sellerId) {
		List<Product> products = sellerDaoImpl.getAllProduct(sellerId);
		return products;
	}

	@Override
	public List<Order> getAllOrders(long sellerId) {
		List<Order> orders = sellerDaoImpl.getAllOrders(sellerId);
		return orders;
	}

	@Override
	public Order getOrder(long orderId) {
		Order order = sellerDaoImpl.getOrder(orderId);
		return order;
	}

	@Override
	public Product getProduct(long productId) {
		Product product = sellerDaoImpl.getProduct(productId);
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
		sellerDaoImpl.cancleOrder(orderId);

	}

}
