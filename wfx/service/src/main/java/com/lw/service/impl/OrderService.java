package com.lw.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lw.entity.dto.Order;
import com.lw.mapper.OrderMapper;

@Service
public class OrderService extends ServiceImpl<OrderMapper, Order>{
	
	/**
	 * 	支付成功后更改订单状态
	 * @param orderId
	 * @return
	 */
	@Transactional
	public boolean updatePayState(String orderId) {
		
		UpdateWrapper<Order> wrapper = new UpdateWrapper<Order>();
		wrapper.lambda()
				.eq(Order::getOrderId,orderId)
				.set(Order::getPayState, 1);
		return this.update(wrapper);
	}

}
