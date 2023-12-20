package com.example.demo.dao;

import java.util.ArrayList;

import com.example.demo.dto.BuyItem;
import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderQueryParams;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Seat;

public interface OrderDao {

	//查詢並取得需要的北上列車座位id含座位類型
	public ArrayList<Seat> getNorthboundSeats(CreateOrderRequest createOrderRequest);
	
	//查詢並取得需要的南下列車座位id含座位類型
	public ArrayList<Seat> getSouthboundSeats(CreateOrderRequest createOrderRequest);

	//初始化訂單並取得id，初始總金額為0
	public Integer createOrder(Integer userId);
	
	//取得訂單id後，加入項目
	public void createOrderItems(CreateOrderRequest createOrderRequest, ArrayList<Seat> seatList, Integer price);

	//加入全部項目後，更新訂單總金額
	public void updateOrderTotalAmountByOrderId(Integer orderId, Integer totalAmount);

	public Order getOrderById(Integer orderId);
	
	public ArrayList<OrderItem> getOrderItemsByOrderId(Integer orderId);

	public void deleteOrder(Integer userId, Integer orderId);

	public void deleteOrderItemsByOrderId(Integer orderId);

	public ArrayList<Order> getOrders(OrderQueryParams orderQueryParams);

	public Integer countOrders(OrderQueryParams orderQueryParams);

	public Integer getOrderTotalAmountByOrderId(Integer orderId);
	
	public void updateOrderItems(ArrayList<OrderItem> orderItemIdList, BuyItem buyItem, ArrayList<Seat> seatList, Integer price);

	public void deleteOrderItemByOrderItemId(Integer orderId, Integer orderItemId);

	public Integer getOrderItemAmountByOrderItemId(Integer orderItemId);
}
