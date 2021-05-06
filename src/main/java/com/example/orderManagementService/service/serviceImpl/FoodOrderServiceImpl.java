package com.example.orderManagementService.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.orderManagementService.constant.OrderConstants;
import com.example.orderManagementService.entity.FoodDto;
import com.example.orderManagementService.entity.FoodOrder;
import com.example.orderManagementService.entity.PaymentDetail;
import com.example.orderManagementService.exception.service.ManageOrderServiceException;
import com.example.orderManagementService.exception.service.custom.CancelOrderException;
import com.example.orderManagementService.exception.service.custom.InsuffecientQtyException;
import com.example.orderManagementService.exception.service.custom.PaymentFailedException;
import com.example.orderManagementService.repository.FoodOrderRepository;
import com.example.orderManagementService.service.FoodOrderService;
import com.example.orderManagementService.util.OrderMAnagementUtil;

@Service
public class FoodOrderServiceImpl implements FoodOrderService {

	@Autowired
	FoodOrderRepository foodRepository;

	@Autowired
	OrderMAnagementUtil manageUtil;

	@Override
	public FoodOrder updateOrder(FoodDto food, long orderId, String updateType)
			throws ManageOrderServiceException {
		FoodOrder foodOrder2 = foodRepository.findById(orderId).orElse(null);
		boolean updateStatus = false;
		List<FoodDto> foodsToUpdate = new ArrayList<>();
		try {
			List<FoodDto> foods = foodOrder2.getFoodsOrdered();
			/* updating qty in orderManagement service db */
			updateFoodQtyInFoodOrder(foods, food, updateType);
			if (updateType.equalsIgnoreCase(OrderConstants.REQUEST_UPDATE_TYPE_INCREASE_QTY)) {
				/* if qty increased then available qty has to be decreased so, setting qty -ve*/
				food.setQty(food.getQty() * -1);
				foodsToUpdate.add(food);
				/* decreasing available qty in resturantSerch service db */
				updateStatus = updateFoodQtyInDb(foodsToUpdate, updateType);
			} else {
				foodsToUpdate.add(food);
				/* increasing available qty in resturantSerch service db */
				updateStatus = updateFoodQtyInDb(foodsToUpdate, updateType);
			}
			if (updateStatus) {
				updatePaymentDetailsAndTotalAmount(food,foodOrder2,updateType);
				foodOrder2 = foodRepository.save(foodOrder2);
			}
		} catch (InsuffecientQtyException e) {
			throw new ManageOrderServiceException(e.getMessage());
		}
		return foodOrder2;
	}

	private void updatePaymentDetailsAndTotalAmount(FoodDto food, FoodOrder foodOrder2, String updateType) {
		double updatedTotalAmount;
		if(updateType.equalsIgnoreCase(OrderConstants.REQUEST_UPDATE_TYPE_DECREASE_QTY)) {
			updatedTotalAmount=foodOrder2.getTotalPrice()-(food.getQty()*food.getPrice());
		}
		else {
			/*since qty is -ve hence multiply it by -1 to get +ve qty*/
			updatedTotalAmount=foodOrder2.getTotalPrice()+(-1*food.getQty()*food.getPrice());
		}
		foodOrder2.getPayment().setAmount(updatedTotalAmount+"");
		foodOrder2.setTotalPrice(updatedTotalAmount);
	}

	private void updateFoodQtyInFoodOrder(List<FoodDto> foods, FoodDto food, String updateType) {
		for (FoodDto foodInDb : foods) {
			if (foodInDb.getId() == food.getId()
					&& updateType.equalsIgnoreCase(OrderConstants.REQUEST_UPDATE_TYPE_INCREASE_QTY)) {
				foodInDb.setQty(food.getQty() + foodInDb.getQty());
			} else if (foodInDb.getId() == food.getId()
					&& updateType.equalsIgnoreCase(OrderConstants.REQUEST_UPDATE_TYPE_DECREASE_QTY)) {
				foodInDb.setQty(foodInDb.getQty() - food.getQty());
			}
		}
	}

	private boolean updateFoodQtyInDb(List<FoodDto> foodsOrdered, String requestStatus)
			throws InsuffecientQtyException {
		FoodDto food2 = null;
		boolean status = true;
		for (FoodDto food : foodsOrdered) {
			food2 = manageUtil.getFoodById(food.getFoodId());
			if (food2 != null && (requestStatus.equalsIgnoreCase(OrderConstants.REQUEST_STATUS_PLACE_ORDER)
					|| requestStatus.equalsIgnoreCase(OrderConstants.REQUEST_UPDATE_TYPE_INCREASE_QTY))) {
				if ((food.getQty() * -1) > food2.getQty()) {
					throw new InsuffecientQtyException("Insufficient Qty please check Qty");
				}
			}
			food2 = manageUtil.updateFoodQtyByFoodId(food);
			if (food2 == null) {
				status = false;
			}

		}
		return status;
	}

	@Override
	public FoodOrder cancelOrder(long orderId, String requestStatus) throws ManageOrderServiceException {
		FoodOrder foodOrder2 = null;
		FoodOrder foodOrder = foodRepository.findById(orderId).orElse(null);
		if (foodOrder.getOrderStatus().equalsIgnoreCase(OrderConstants.ORDER_STATUS_CANCELED)) {
			throw new CancelOrderException("This Order is Already Cancelled");
		}
		List<FoodDto> foodsOrdered = foodOrder.getFoodsOrdered().stream().map(x -> updateQty(requestStatus, x))
				.collect(Collectors.toList());
		try {
			boolean updateStatus = updateFoodQtyInDb(foodsOrdered, requestStatus);
			if (updateStatus) {
				foodOrder.getPayment().setPaymentStatus(OrderConstants.PAYMENT_STATUS_ON_CANCELED_ORDER);
				foodOrder.setOrderStatus(OrderConstants.ORDER_STATUS_CANCELED);
				foodOrder2 = foodRepository.save(foodOrder);
			}
		} catch (InsuffecientQtyException e) {
			throw new ManageOrderServiceException(e.getMessage());
		}
		return foodOrder2;
	}

	private FoodDto updateQty(String requestStatus, FoodDto x) {
		/* setting up qty to reduce qty in food db after order get placed */
		if (requestStatus.equalsIgnoreCase(OrderConstants.REQUEST_STATUS_PLACE_ORDER)) {
			/*-ve sign will decrease available qty in db*/
			x.setQty(x.getQty() * -1);
		} else {
			/* setting up qty to increase qty in food db after order cancellation */
			x.setQty(x.getQty() * 1);
		}
		return x;
	}

	@Override
	public FoodOrder placeOrder(FoodOrder foodOrder, String requestStatus) throws ManageOrderServiceException {
		FoodOrder foodOrder2 = null;
		boolean updateStatus = false;
		PaymentDetail paymentDetail = null;
		List<FoodDto> list = foodOrder.getFoodsOrdered();
		List<FoodDto> foodsOrdered = list.stream().map(x -> updateQty(requestStatus, x)).collect(Collectors.toList());
		try {
			updateStatus = updateFoodQtyInDb(foodsOrdered, requestStatus);
			if (updateStatus) {
				paymentDetail = processPayment(foodOrder);
				processOrderInFoodDto(foodOrder);
			}
			if (paymentDetail != null) {
				foodOrder.setOrderStatus(OrderConstants.ORDER_STATUS_ACCEPTED);
				foodOrder2 = foodRepository.save(foodOrder);
			}
		} catch (InsuffecientQtyException e) {
			throw new ManageOrderServiceException(e.getMessage());
		}
		return foodOrder2;

	}

	private void processOrderInFoodDto(FoodOrder foodOrder) {
		double totalPrice = 0;
		List<FoodDto> foodList = foodOrder.getFoodsOrdered();
		for (FoodDto food : foodList) {
			food.setQty(food.getQty() * (-1));
			totalPrice += (food.getQty() * food.getPrice());
			food.setOrder(foodOrder);
		}
		foodOrder.setTotalPrice(totalPrice);
		foodOrder.setFoodsOrdered(foodList);
	}

	private PaymentDetail processPayment(FoodOrder foodOrder) throws PaymentFailedException {
		PaymentDetail paymentDetail2 = foodOrder.getPayment();
		if (paymentDetail2 != null
				&& !paymentDetail2.getPaymentStatus().equalsIgnoreCase(OrderConstants.PAYMENT_STATUS_PAID)) {
			throw new PaymentFailedException("Payment Failed");
		}
		paymentDetail2.setOrder(foodOrder);
		return paymentDetail2;
	}

}
