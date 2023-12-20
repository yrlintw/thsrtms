package com.example.demo.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.OrderDao;
import com.example.demo.dao.TicketDao;
import com.example.demo.dao.TrainDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.BuyItem;
import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderQueryParams;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Seat;
import com.example.demo.model.User;

@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TrainDao trainDao;
	
	@Autowired
	private TicketDao ticketDao;
	
	private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Transactional
	@Override
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
		
		User user = userDao.getUserById(userId);
		
		if (user == null) {
			log.warn("userId {} 不存在", userId);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		//訂單總金額
		int totalAmount = 0;
		
		//查詢列車id並加入創建訂單物件中
		createOrderRequest.setTrainNumId(trainDao.getTrainNumId(createOrderRequest.getTrainNum(), createOrderRequest.getRunDate()));
		
		//預訂完成的座位
		ArrayList<Seat> seatList = new ArrayList<>();
		
		//車號偶數北上，奇數南下
		if (createOrderRequest.getTrainNum() % 2 == 0) {
			//取得適合的座位
			seatList = orderDao.getNorthboundSeats(createOrderRequest);
			if (seatList.size() == 0) {
				log.warn("{}運行的{}號北上列車，在指定的查詢條件下，沒有剩餘對號座位", createOrderRequest.getRunDate(), createOrderRequest.getTrainNum());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
				
		} else {
			seatList = orderDao.getSouthboundSeats(createOrderRequest);
			if (seatList.size() == 0) {
				log.warn("{}運行的{}號南下列車，在指定的查詢條件下，沒有剩餘對號座位", createOrderRequest.getRunDate(), createOrderRequest.getTrainNum());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
		}
			
		//有座位可以預訂，創建主訂單，總金額未計算，預設為0
		if (createOrderRequest.getOrderId() == -1) {
			Integer orderId = orderDao.createOrder(userId);
			createOrderRequest.setOrderId(orderId);
		}
			
		//當前車票的票價
		Integer price = ticketDao.getTicketPrice(createOrderRequest);
		//創建車票物件
		orderDao.createOrderItems(createOrderRequest, seatList, price);
		//計算總金額
		totalAmount += createOrderRequest.getQuantity() * price;
		
		
		if (createOrderRequest.getOrderId() != -1) {
			orderDao.updateOrderTotalAmountByOrderId(createOrderRequest.getOrderId(), totalAmount);
		}
		return createOrderRequest.getOrderId();
	}

	@Override
	public Order getOrderById(Integer orderId) {
		Order order = orderDao.getOrderById(orderId);
		
		if (order == null) {
			log.warn("orderId {} 不存在", orderId);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		ArrayList<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
		order.setOrderItemList(orderItemList);
		return order;
	}

	@Override
	public ArrayList<Order> getOrders(OrderQueryParams orderQueryParams) {
		ArrayList<Order> orderList = orderDao.getOrders(orderQueryParams);
		
		for (Order order: orderList) {
			ArrayList<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());
			order.setOrderItemList(orderItemList);
		}
		return orderList;
	}

	@Override
	public Integer countOrders(OrderQueryParams orderQueryParams) {
		return orderDao.countOrders(orderQueryParams);
	}

	@Transactional
	@Override
	public Order updateOrder(Integer userId, Integer orderId, BuyItem buyItem) {
		
		/*User user = userDao.getUserById(userId);
		
		if (user == null) {
			log.warn("userId {} 不存在", userId);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		//檢查資料庫的資料是否和想修改的部分不衝突
		ArrayList<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
		
		ArrayList<OrderItem> targetOrderItemList = new ArrayList<>();
		ArrayList<OrderItem> otherOrderItemList = new ArrayList<>();
		
		for (int i = 0; i < orderItemList.size(); i++) {
			if (orderItemList.get(i).getStartStationId() == buyItem.getStationStartId() 
					&& orderItemList.get(i).getEndStationId() == buyItem.getStationEndId()) {
				targetOrderItemList.add(orderItemList.get(i));
				
			} else {
				otherOrderItemList.add(orderItemList.get(i));
			}
		}
		
		if (targetOrderItemList.size() == 0) {
			log.warn("訂單的起站: {} 迄站: {} 不可變更", orderItemList.get(0).getStartStationName(), orderItemList.get(0).getEndStationName());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		//修改的車票張數要和資料庫中的數量一致
		if (targetOrderItemList.size() != buyItem.getQuantity()) {
			log.warn("訂單的車票數量: {} 不可變更", targetOrderItemList.size());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		//原訂單總金額扣掉要修改車票的總金額
		int dbTotalAmount = orderDao.getOrderTotalAmountByOrderId(orderId);
		for (OrderItem orderItem: targetOrderItemList) {
			CreateOrderRequest temp = new CreateOrderRequest();
			temp.setStationStartId(orderItem.getStartStationId());
			temp.setStationEndId(orderItem.getEndStationId());
			temp.setSeatTypeId(orderItem.getSeatTypeId());
			temp.setTicketTypeId(orderItem.getTicketTypeId());
			dbTotalAmount -= ticketDao.getTicketPrice(temp);
		}
		
		//新訂單總金額
		int totalAmount = 0;

		//buyItem to createOrderRequest
		CreateOrderRequest createOrderRequest = new CreateOrderRequest();
		
		
		//車票內容:車號偶數北上，奇數南下
		TrainNum trainNum = trainDao.getTrainNumById(buyItem.getTrainNumId());
		ArrayList<Seat> seatList = new ArrayList<>();
		
		if (trainNum.getTrainNum() % 2 == 0) {
			//取得適合的座位
			seatList = orderDao.getNorthboundSeats(buyItem);
			if (seatList.size() == 0) {
				log.warn("{} 運行的 {} 號北上列車，在指定的查詢條件下，沒有剩餘對號座位", trainNum.getRunDate(), trainNum.getTrainNum());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			
		} else {
			seatList = orderDao.getSouthboundSeats(buyItem);
			if (seatList.size() == 0) {
				log.warn("{} 運行的 {} 號南下列車，在指定的查詢條件下，沒有剩餘對號座位", trainNum.getRunDate(), trainNum.getTrainNum());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
		}
		
		//當前車票的票價
		Integer price = ticketDao.getTicketPrice(buyItem);
		//更新車票物件
		orderDao.updateOrderItems(targetOrderItemList, buyItem, seatList, price);
		//計算被修改車票的總金額
		totalAmount += buyItem.getQuantity() * price;
		//計算訂單中其他車票的總金額
		totalAmount += dbTotalAmount;
		//更新主訂單總金額
		orderDao.updateOrderTotalAmountByOrderId(orderId, totalAmount);
		
		Order order = orderDao.getOrderById(orderId);
		orderItemList.clear();
		orderItemList = orderDao.getOrderItemsByOrderId(orderId);
		order.setOrderItemList(orderItemList);*/
		
		return null;//order;
	}

	@Override
	public void deleteOrder(Integer userId, Integer orderId) {
		orderDao.deleteOrder(userId, orderId);
	}

	@Override
	public void deleteOrderItemsByOrderId(Integer orderId) {
		orderDao.deleteOrderItemsByOrderId(orderId);
	}

	@Override
	public Integer deleteOrderItemByOrderItemId(Integer orderId, Integer orderItemId) {
		Integer amount = orderDao.getOrderItemAmountByOrderItemId(orderItemId);
		orderDao.deleteOrderItemByOrderItemId(orderId, orderItemId);
		return amount;
	}

	@Override
	public void updateOrderTotalAmount(Integer userId, Integer orderId, Integer amount) {
		Integer totalAmount = orderDao.getOrderTotalAmountByOrderId(orderId);
		totalAmount -= amount;
		orderDao.updateOrderTotalAmountByOrderId(orderId, totalAmount);
		
		if (totalAmount == 0) {
			orderDao.deleteOrder(userId, orderId);
		}
	}
	
	/*private boolean containsObject(final List<OrderItem> list, 
			final Integer stationStartId, final Integer stationEndId) {
	    
		return list.stream().filter(o -> 
	    		o.getStartStationId().equals(stationStartId) 
	    		& o.getEndStationId().equals(stationEndId)
	    		).findFirst().isPresent();
	}*/
}
