package com.example.orderManagementService.dto;

public class ErrorDto {

	String errorMessage;
	Object error;
	public ErrorDto() {
	}
	public ErrorDto(String errorMessage, Object error) {
		super();
		this.errorMessage = errorMessage;
		this.error = error;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Object getError() {
		return error;
	}
	public void setError(Object error) {
		this.error = error;
	}
	
}
