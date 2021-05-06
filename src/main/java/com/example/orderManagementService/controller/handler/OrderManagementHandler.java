package com.example.orderManagementService.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.orderManagementService.dto.ErrorDto;
import com.example.orderManagementService.dto.ResponseBody;
import com.example.orderManagementService.exception.OrderManagementServiceException;

@RestControllerAdvice
public class OrderManagementHandler {

	@ExceptionHandler(OrderManagementServiceException.class)
	public ResponseEntity<?> errorHandler(Exception e){
		ErrorDto errdto=new ErrorDto(e.getMessage(), e);
		ResponseBody<Void> response=new ResponseBody<>(errdto,null);
		return new ResponseEntity<ResponseBody<Void>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
