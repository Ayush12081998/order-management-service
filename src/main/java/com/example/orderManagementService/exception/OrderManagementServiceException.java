package com.example.orderManagementService.exception;

public class OrderManagementServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderManagementServiceException() {
		super();
		
	}

	public OrderManagementServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public OrderManagementServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public OrderManagementServiceException(String message) {
		super(message);
		
	}

	public OrderManagementServiceException(Throwable cause) {
		super(cause);
		
	}

	
}
