package com.example.orderManagementService.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.orderManagementService.constant.OrderConstants;
import com.example.orderManagementService.entity.FoodDto;
import com.example.orderManagementService.entity.FoodOrder;
import com.example.orderManagementService.entity.PaymentDetail;
import com.example.orderManagementService.exception.service.ManageOrderServiceException;
import com.example.orderManagementService.repository.FoodOrderRepository;
import com.example.orderManagementService.service.FoodOrderService;
import com.example.orderManagementService.util.OrderMAnagementUtil;

@ExtendWith(SpringExtension.class)
class FoodOrderServiceImplTest {

	@TestConfiguration
	static class TestConfig {
		@Bean
		public FoodOrderService employeeService() {
			return new FoodOrderServiceImpl();
		}
	}

	@Autowired
	FoodOrderService orderService;

	@MockBean
	FoodOrderRepository foodRepository;

	@MockBean
	OrderMAnagementUtil orderMAnagementUtil;
	
	@Test
	public void cancelOrderTest() throws Exception {
		FoodOrder order = getOneOrderStub(OrderConstants.ORDER_STATUS_ACCEPTED);
		FoodDto food = getOneFoodDtoStub(1L, 100, 2);
		when(foodRepository.findById(1L)).thenReturn(Optional.of(order));
		when(orderMAnagementUtil.getFoodById(1L)).thenReturn(getOneFoodDtoStub(1L, 100, 100));
		when(orderMAnagementUtil.updateFoodQtyByFoodId(food)).thenReturn(food);
		when(foodRepository.save(order)).thenReturn(order);
		orderService.cancelOrder(order.getOrderId(), OrderConstants.REQUEST_STATUS_CANCEL_ORDER);
	}

	@Test
	public void cancelOrderTestAlreadyCancelledOrder() throws Exception {
		FoodOrder order = getOneOrderStub(OrderConstants.ORDER_STATUS_CANCELED);
		when(foodRepository.findById(1L)).thenReturn(Optional.of(order));
		assertThrows(ManageOrderServiceException.class, () -> {
			orderService.cancelOrder(order.getOrderId(), OrderConstants.REQUEST_STATUS_CANCEL_ORDER);
		});
	}

	@Test
	public void placeOrderTest() throws Exception {
		FoodOrder order = getOneOrderStub(OrderConstants.ORDER_STATUS_ACCEPTED);
		FoodDto food = getOneFoodDtoStub(1L, 100, 2);
		when(orderMAnagementUtil.getFoodById(1L)).thenReturn(getOneFoodDtoStub(1L, 100, 100));
		when(orderMAnagementUtil.updateFoodQtyByFoodId(food))
		.thenReturn(Mockito.any(FoodDto.class));
		when(foodRepository.save(order)).thenReturn(order);
		orderService.placeOrder(order, OrderConstants.REQUEST_STATUS_PLACE_ORDER);
	}

	@Test
	public void placeOrderTestQtyMoreThanAvailableQty() throws Exception {
		FoodOrder order = getOneOrderStub(OrderConstants.ORDER_STATUS_ACCEPTED);
		order.getFoodsOrdered().get(0).setQty(102);
		when(orderMAnagementUtil.getFoodById(1L)).thenReturn(getOneFoodDtoStub(1L, 100, 100));
		assertThrows(ManageOrderServiceException.class, () -> {
			orderService.placeOrder(order, OrderConstants.REQUEST_STATUS_PLACE_ORDER);
		});
	}

	@Test
	public void updateOrderTestWithQtyIncrease() throws Exception {
		FoodOrder order = getOneOrderStub(OrderConstants.ORDER_STATUS_ACCEPTED);
		FoodDto food = getOneFoodDtoStub(1L, 100, 2);
		when(foodRepository.findById(1L)).thenReturn(Optional.of(order));
		when(orderMAnagementUtil.getFoodById(1L)).thenReturn(getOneFoodDtoStub(1L, 100, 100));
		when(orderMAnagementUtil.updateFoodQtyByFoodId(food)).thenReturn(food);
		when(foodRepository.save(order)).thenReturn(order);
		orderService.updateOrder(food, order.getOrderId(), OrderConstants.REQUEST_UPDATE_TYPE_INCREASE_QTY);
	}
	
	@Test
	public void updateOrderTestWithQtyIncreaseInsufficientQty() throws Exception {
		FoodOrder order = getOneOrderStub(OrderConstants.ORDER_STATUS_ACCEPTED);
		FoodDto food = getOneFoodDtoStub(1L, 100, 102);
		when(foodRepository.findById(1L)).thenReturn(Optional.of(order));
		when(orderMAnagementUtil.getFoodById(1L)).thenReturn(getOneFoodDtoStub(1L, 100, 100));
		assertThrows(ManageOrderServiceException.class, () -> {
			orderService.updateOrder(food, order.getOrderId(), OrderConstants.REQUEST_UPDATE_TYPE_INCREASE_QTY);
		});
	}
	
	@Test
	public void updateOrderTestWithQtyDecrease() throws Exception {
		FoodOrder order = getOneOrderStub(OrderConstants.ORDER_STATUS_ACCEPTED);
		FoodDto food = getOneFoodDtoStub(1L, 100, 2);
		when(foodRepository.findById(1L)).thenReturn(Optional.of(order));
		when(orderMAnagementUtil.getFoodById(1L)).thenReturn(getOneFoodDtoStub(1L, 100, 100));
		when(orderMAnagementUtil.updateFoodQtyByFoodId(food)).thenReturn(getOneFoodDtoStub(2L, 100, 2));
		when(foodRepository.save(order)).thenReturn(order);
		orderService.updateOrder(food, order.getOrderId(), OrderConstants.REQUEST_UPDATE_TYPE_DECREASE_QTY);
	}

	private FoodOrder getOneOrderStub(String orderStatus) {
		FoodOrder order = new FoodOrder();
		order.setOrderId(1L);
		order.setOrderStatus(orderStatus);
		order.setTotalPrice(1000);
		order.setFoodsOrdered(getFoodOrderListStub(1L, 100, 2));
		order.setPayment(getOnePaymentDetailStub(OrderConstants.PAYMENT_STATUS_PAID, "1000", "AYUSH"));
		return order;
	}

	private PaymentDetail getOnePaymentDetailStub(String paymentStatusPaid, String amount, String name) {
		PaymentDetail payDetail = new PaymentDetail();
		payDetail.setAmount(amount);
		payDetail.setPaymentId(1L);
		payDetail.setPaymentMethod("CARD");
		payDetail.setPaymentStatus(paymentStatusPaid);
		payDetail.setUserName(name);
		return payDetail;
	}

	private List<FoodDto> getFoodOrderListStub(long foodId, double price, int qty) {
		List<FoodDto> foodList = new ArrayList<>();
		FoodDto food = getOneFoodDtoStub(foodId, price, qty);
		foodList.add(food);
		return foodList;
	}

	private FoodDto getOneFoodDtoStub(long foodId, double price, int qty) {
		FoodDto food = new FoodDto();
		food.setFoodId(foodId);
		food.setId(1L);
		food.setPrice(price);
		food.setQty(qty);
		return food;
	}

}
