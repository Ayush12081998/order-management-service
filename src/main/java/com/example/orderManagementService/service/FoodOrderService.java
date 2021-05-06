package com.example.orderManagementService.service;

import org.springframework.stereotype.Service;

import com.example.orderManagementService.entity.FoodDto;
import com.example.orderManagementService.entity.FoodOrder;
import com.example.orderManagementService.exception.service.ManageOrderServiceException;

@Service
public interface FoodOrderService {

	public FoodOrder placeOrder(FoodOrder foodOrder,String requestStatus) throws ManageOrderServiceException;
	public FoodOrder cancelOrder(long orderId, String requestStatus) throws ManageOrderServiceException;
	public FoodOrder updateOrder(FoodDto food,long orderId, String updateType)
			throws ManageOrderServiceException;
}
