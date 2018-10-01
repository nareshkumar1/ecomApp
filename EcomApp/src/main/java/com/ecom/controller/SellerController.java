package com.ecom.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.hibernate.modal.Order;
import com.ecom.hibernate.modal.Product;
import com.ecom.hibernate.modal.Seller;
import com.ecom.service.iface.SellerService;

@RestController
@RequestMapping("/seller")
public class SellerController {

	@Autowired
	SellerService sellerService;

	@PostMapping(value = "/createSeller", consumes = "application/json")
	public ResponseEntity<Void> createSeller(@RequestBody Seller seller) {
		boolean isExists = sellerService.createSeller(seller);
		if (isExists) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
	}

	@PostMapping(value = "/uploadProduct", consumes = "application/json")
	public ResponseEntity<Void> uploadNewItem(@RequestBody Map<String, String> productDetail) {
		sellerService.uploadNewProduct(productDetail);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping(value = "/{sellerId}/products", produces = "application/json")
	public ResponseEntity<List<Product>> getAllProducts(@PathVariable long sellerId) {
		List<Product> products = sellerService.getAllProduct(sellerId);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@GetMapping(value = "/{sellerId}/orders", produces = "application/json")
	public ResponseEntity<List<Order>> getAllOrders(@PathVariable long sellerId) {
		List<Order> orders = sellerService.getAllOrders(sellerId);
		return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
	}

	@GetMapping(value = "/order/{orderId}", produces = "application/json")
	public ResponseEntity<Order> getOrder(@PathVariable long orderId) {
		Order order = sellerService.getOrder(orderId);
		if (order == null) {
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Order>(order, HttpStatus.OK);
		}
	}

	@GetMapping(value = "/product/{productId}", produces = "application/json")
	public ResponseEntity<Product> getProduct(@PathVariable long productId) {

		Product product = sellerService.getProduct(productId);
		if (product == null) {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		}
	}

	@DeleteMapping(value = "/order/{orderId}")
	public ResponseEntity<Void> cancelOrder(@PathVariable long orderId) {
		sellerService.cancleOrder(orderId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
