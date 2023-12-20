package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.BuyItem;
import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderQueryParams;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import com.example.demo.util.Page;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Controller
@Validated
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/users/{userId}/orders")
	public String create(@PathVariable Integer userId, 
			@ModelAttribute("createOrderRequest") CreateOrderRequest createOrderRequest, 
			Model model) {
		
		Integer orderId = orderService.createOrder(userId, createOrderRequest);
		Order order = orderService.getOrderById(orderId);
		
		model.addAttribute("order", order);
		
		return "show-order";
	} 

	@GetMapping("/users/{userId}/orders")
	public ResponseEntity<Page<Order>> read(@PathVariable Integer userId, 
			@RequestParam(defaultValue = "10") @Max(100) @Min(0) Integer limit,
			@RequestParam(defaultValue = "0") @Min(0) Integer offset) {
		
		OrderQueryParams orderQueryParams = new OrderQueryParams();
		orderQueryParams.setUserId(userId);
		orderQueryParams.setLimit(limit);
		orderQueryParams.setOffset(offset);
		
		ArrayList<Order> list = orderService.getOrders(orderQueryParams);
		Integer total = orderService.countOrders(orderQueryParams);
		
		Page<Order> page = new Page<>();
		page.setLimit(limit);
		page.setOffset(offset);
		page.setTotal(total);
		page.setResults(list);
		
		return ResponseEntity.status(HttpStatus.OK).body(page);
	}
	
	//乘車日期、時間、車號、座位類型、座位偏好、票種之變更（起訖站及車票數量不可變更）
	@PutMapping("/users/{userId}/orders/{orderId}")
	public ResponseEntity<Order> update(@PathVariable Integer userId, 
			@PathVariable Integer orderId, @RequestBody BuyItem buyItem) {
		
		Order order = orderService.updateOrder(userId, orderId, buyItem);

		return ResponseEntity.status(HttpStatus.OK).body(order);
	} 
	
	@DeleteMapping("/users/{userId}/orders/{orderId}")
	public ResponseEntity<?> delete(@PathVariable Integer userId, 
			@PathVariable Integer orderId) {
		
		orderService.deleteOrder(userId, orderId);
		orderService.deleteOrderItemsByOrderId(orderId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	} 
	
	@DeleteMapping("/users/{userId}/orders/{orderId}/orderItems/{orderItemId}")
	public ResponseEntity<?> deleteOrderItems(@PathVariable Integer userId, 
			@PathVariable Integer orderId, @PathVariable Integer orderItemId) {
		
		//被刪除的車票金額
		Integer amount = orderService.deleteOrderItemByOrderItemId(orderId, orderItemId);
		orderService.updateOrderTotalAmount(userId, orderId, amount);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	} 
}