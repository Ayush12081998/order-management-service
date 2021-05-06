package com.example.orderManagementService.exception.service.custom;

import com.example.orderManagementService.exception.service.ManageOrderServiceException;

public class PaymentFailedException extends ManageOrderServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaymentFailedException() {
		super();
		
	}

	public PaymentFailedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public PaymentFailedException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public PaymentFailedException(String message) {
		super(message);
		
	}

	public PaymentFailedException(Throwable cause) {
		super(cause);
		
	}

	
}
