package com.example.orderManagementService.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_details")
public class PaymentDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;
	private String amount;
	private String userName;
	private String paymentMethod;
	private String paymentStatus;
	@OneToOne
	private FoodOrder order;
	@Override
	public String toString() {
		return "PaymentDetail [paymentId=" + paymentId + ", amount=" + amount + ", userName=" + userName
				+ ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus + "]";
	}
	
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public FoodOrder getOrder() {
		return order;
	}
	public void setOrder(FoodOrder order) {
		this.order = order;
	}
	
	
}
