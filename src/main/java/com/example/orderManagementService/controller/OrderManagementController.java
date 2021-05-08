package com.example.orderManagementService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderManagementService.dto.ResponseBody;
import com.example.orderManagementService.entity.FoodDto;
import com.example.orderManagementService.entity.FoodOrder;
import com.example.orderManagementService.exception.OrderManagementServiceException;
import com.example.orderManagementService.service.FoodOrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderManagementController {

	@Autowired
	private FoodOrderService foodOrderService;

	@PutMapping("/{updateType}/{orderId}")
	@HystrixCommand(fallbackMethod = "fallBackMethodOnResturantServiceFailure")
	public ResponseEntity<?> updateOrder(@RequestBody FoodDto food, @PathVariable Long orderId, @PathVariable String updateType)
			throws OrderManagementServiceException {
		return new ResponseEntity<ResponseBody<FoodOrder>>(new ResponseBody<FoodOrder>(null,foodOrderService.updateOrder(food, orderId, updateType)), HttpStatus.OK);
	}

	@GetMapping("/{requestStatus}/{orderId}")
	@HystrixCommand(fallbackMethod = "fallBackMethodOnResturantServiceFailure")
	public ResponseEntity<?> cancelOrder(@PathVariable String requestStatus, @PathVariable long orderId)
			throws OrderManagementServiceException {
		return new ResponseEntity<ResponseBody<FoodOrder>>(new ResponseBody<FoodOrder>(null,foodOrderService.cancelOrder(orderId, requestStatus)), HttpStatus.OK);
	}

	@PostMapping("/{requestStatus}")
	@HystrixCommand(fallbackMethod = "fallBackMethodOnResturantServiceFailure")
	public ResponseEntity<?> placeOrder(@RequestBody FoodOrder foodOrder, @PathVariable String requestStatus)
			throws OrderManagementServiceException {
		return new ResponseEntity<ResponseBody<FoodOrder>>(new ResponseBody<FoodOrder>(null,foodOrderService.placeOrder(foodOrder, requestStatus)), HttpStatus.OK);
	}
	
	public ResponseEntity<?> fallBackMethodOnResturantServiceFailure(@RequestBody FoodDto food, @PathVariable Long orderId, @PathVariable String updateType){
		return new ResponseEntity<String>
		("Resturant Search Service is Taking Too Long To Respond", HttpStatus.FAILED_DEPENDENCY);
	}
	
	public ResponseEntity<?> fallBackMethodOnResturantServiceFailure(@PathVariable String requestStatus, @PathVariable long orderId){
		return new ResponseEntity<String>
		("Resturant Search Service is Taking Too Long To Respond", HttpStatus.FAILED_DEPENDENCY);
	}
	
	public ResponseEntity<?> fallBackMethodOnResturantServiceFailure(@RequestBody FoodOrder foodOrder, @PathVariable String requestStatus){
		return new ResponseEntity<String>
		("Resturant Search Service is Taking Too Long To Respond", HttpStatus.FAILED_DEPENDENCY);
	}
}
