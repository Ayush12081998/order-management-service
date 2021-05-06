package com.example.orderManagementService.constant;

public class OrderConstants {

	public static final String FOOD_URL="http://localhost:9090/api/v1/foods";
	public static final String REQUEST_STATUS_PLACE_ORDER="PLACE";
	public static final String REQUEST_STATUS_UPDATE_ORDER="UPDATE";
	public static final String REQUEST_STATUS_CANCEL_ORDER="CANCEL";
	public static final String REQUEST_UPDATE_TYPE_INCREASE_QTY="ADD";
	public static final String REQUEST_UPDATE_TYPE_DECREASE_QTY="REMOVE";
	public static final String ORDER_STATUS_ACCEPTED="ACCEPTED";
	public static final String ORDER_STATUS_CANCELED="CANCELED";
	public static final String PAYMENT_STATUS_PAID="PAID";
	public static final String PAYMENT_STATUS_ON_CANCELED_ORDER="REFUND";
}
