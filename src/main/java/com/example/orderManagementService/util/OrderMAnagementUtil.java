package com.example.orderManagementService.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.orderManagementService.constant.OrderConstants;
import com.example.orderManagementService.entity.FoodDto;

@Service
public class OrderMAnagementUtil {

	@Autowired
	RestTemplate restTemplate;
	
	public FoodDto getFoodById(long foodId) {
		FoodDto food =restTemplate.getForObject(OrderConstants.FOOD_URL+"/"+foodId, FoodDto.class);
		return food;
	}
	
	public FoodDto updateFoodQtyByFoodId(FoodDto food) {
		HttpEntity<FoodDto> request = new HttpEntity<>(food);
		ResponseEntity<FoodDto> response = restTemplate
		  .exchange(OrderConstants.FOOD_URL, HttpMethod.POST, request, FoodDto.class);
		return response.getBody();
	}
}
