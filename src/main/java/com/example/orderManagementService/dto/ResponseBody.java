package com.example.orderManagementService.dto;

public class ResponseBody<T> {

	ErrorDto error;
	Object result;
	public ResponseBody() {
		// TODO Auto-generated constructor stub
	}
	public ResponseBody(ErrorDto error, Object result) {
		super();
		this.error = error;
		this.result = result;
	}
	public ErrorDto getError() {
		return error;
	}
	public void setError(ErrorDto error) {
		this.error = error;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	
}
