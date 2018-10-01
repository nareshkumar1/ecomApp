package com.ecom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.hibernate.modal.EcomUser;
import com.ecom.hibernate.modal.Order;
import com.ecom.service.iface.EcomUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	EcomUserService userService;
	
	int i = 0;

	@PostMapping(value = "/createUser", consumes = "application/json")
	public ResponseEntity<String> createUser(@RequestBody EcomUser user) {
		boolean isExists = userService.createUser(user);
		if (isExists) {
			return new ResponseEntity<String>("User already Exists", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<String>("User created", HttpStatus.CREATED);
		}
	}

	@PutMapping(value = "/changePassword", consumes = "application/json")
	public ResponseEntity<Void> changePassword(@RequestBody Map<String, String> passwordData) {
		userService.updatePassword(Long.parseLong(passwordData.get("userId")), passwordData.get("newPassword"));
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping(value = "/addItemToCart", consumes = "application/json")
	public ResponseEntity<Void> addItemToCart(@RequestBody Map<String, String> itemData) {
		userService.addItemToCart(Long.parseLong(itemData.get("userId")), Long.parseLong(itemData.get("productId")));
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping(value = "/createOrder", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Map<String, Long>> createNewOrder(@RequestBody Map<String, String> orderData) {
		Map<String, Long> orderId = new HashMap<>();
		long id = userService.createOrder(Long.parseLong(orderData.get("userId")),Long.parseLong(orderData.get("productId")));
		orderId.put("orderId", id);
		return new ResponseEntity<Map<String, Long>>(orderId, HttpStatus.OK);
	}

	@GetMapping(value = "/{userId}/orders", produces = "application/json")
	public ResponseEntity<List<Order>> getAllOrder(@PathVariable long userId) {
		List<Order> orders = userService.getAllOrders(userId);
		return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
	}

	@GetMapping(value = "/order/{orderId}", produces = "application/json")
	public ResponseEntity<Order> getOrder(@PathVariable long orderId) {
		Order order = userService.getOrder(orderId);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@DeleteMapping(value = "/cancelOrder/{orderId}", produces = "application/json")
	public ResponseEntity<Void> cancelOrder(@PathVariable long orderId) {
		userService.cancelOrder(orderId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/counter")
	public int counter() {
		return ++i;
	}

}
