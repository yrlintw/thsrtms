package com.example.demo.service;

import java.util.ArrayList;

import com.example.demo.dto.BuyItem;
import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderQueryParams;
import com.example.demo.model.Order;

public interface OrderService {
	
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
	
	public Order getOrderById(Integer orderId);
	
	public ArrayList<Order> getOrders(OrderQueryParams orderQueryParams);
	
	public Integer countOrders(OrderQueryParams orderQueryParams);
	
	public Order updateOrder(Integer userId, Integer orderId, BuyItem buyItem);
	
	public void deleteOrder(Integer userId, Integer orderId);

	public void deleteOrderItemsByOrderId(Integer orderId);

	public Integer deleteOrderItemByOrderItemId(Integer orderId, Integer orderItemId);

	public void updateOrderTotalAmount(Integer userId, Integer orderId, Integer amount);
}
