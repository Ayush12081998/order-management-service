package com.example.orderManagementService.exception.service;

import com.example.orderManagementService.exception.OrderManagementServiceException;

public class ManageOrderServiceException extends OrderManagementServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ManageOrderServiceException() {
		super();
		
	}

	public ManageOrderServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public ManageOrderServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ManageOrderServiceException(String message) {
		super(message);
		
	}

	public ManageOrderServiceException(Throwable cause) {
		super(cause);
		
	}
	

}
