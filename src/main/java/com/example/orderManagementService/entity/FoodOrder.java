package com.example.orderManagementService.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_orders")
public class FoodOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	private String orderStatus;
	@OneToMany(mappedBy = "order",cascade = {CascadeType.PERSIST})
	@JsonIgnoreProperties("order")
	private List<FoodDto>foodsOrdered;
	private double totalPrice;
	@OneToOne(mappedBy = "order",fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST})
	@JsonIgnoreProperties("order")
	private PaymentDetail payment;
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<FoodDto> getFoodsOrdered() {
		return foodsOrdered;
	}

	public void setFoodsOrdered(List<FoodDto> foodsOrdered) {
		this.foodsOrdered = foodsOrdered;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	
	public PaymentDetail getPayment() {
		return payment;
	}

	public void setPayment(PaymentDetail payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "FoodOrder [orderId=" + orderId + ", orderStatus=" + orderStatus
				+ ", totalPrice=" + totalPrice + "]";
	}
	
}
