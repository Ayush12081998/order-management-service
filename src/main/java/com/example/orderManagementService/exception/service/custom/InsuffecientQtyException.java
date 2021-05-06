package com.example.orderManagementService.exception.service.custom;

import com.example.orderManagementService.exception.service.ManageOrderServiceException;

public class InsuffecientQtyException extends ManageOrderServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsuffecientQtyException() {
		super();
		
	}

	public InsuffecientQtyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public InsuffecientQtyException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public InsuffecientQtyException(String message) {
		super(message);
		
	}

	public InsuffecientQtyException(Throwable cause) {
		super(cause);
		
	}

	
}
