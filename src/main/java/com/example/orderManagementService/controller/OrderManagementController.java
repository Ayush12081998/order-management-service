package com.example.orderManagementService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderManagementService.entity.FoodDto;
import com.example.orderManagementService.entity.FoodOrder;
import com.example.orderManagementService.exception.OrderManagementServiceException;
import com.example.orderManagementService.service.FoodOrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderManagementController {

	@Autowired
	private FoodOrderService foodOrderService;
//	@Autowired
//	private FoodOrderRepository repository;
//
//	@GetMapping
//	public List<FoodOrder> getAllFoodOrders() {
//		List<FoodOrder> list = repository.findAll();
//		return list;
//	}

	@PutMapping("/{updateType}/{orderId}")
	public FoodOrder updateOrder(@RequestBody FoodDto food, @PathVariable Long orderId, @PathVariable String updateType)
			throws OrderManagementServiceException {
		return foodOrderService.updateOrder(food, orderId, updateType);
	}

	@GetMapping("/{requestStatus}/{orderId}")
	public FoodOrder cancelOrder(@PathVariable String requestStatus, @PathVariable long orderId)
			throws OrderManagementServiceException {
		return foodOrderService.cancelOrder(orderId, requestStatus);
	}

	@PostMapping("/{requestStatus}")
	public FoodOrder placeOrder(@RequestBody FoodOrder foodOrder, @PathVariable String requestStatus)
			throws OrderManagementServiceException {
		return foodOrderService.placeOrder(foodOrder, requestStatus);
	}
}
