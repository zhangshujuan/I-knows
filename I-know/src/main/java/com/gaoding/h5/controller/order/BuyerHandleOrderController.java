package com.gaoding.h5.controller.order;

import com.gaoding.domain.po.order.FuwuOrder;
import com.gaoding.service.order.OrderBizService;
import com.gaoding.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Title: BuyerHandleOrderController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月16日 下午4:02 <br>
 *
 * @author Josh Wang
 */
@Controller
public class BuyerHandleOrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderBizService orderBizService;

	@RequestMapping(value = "/order/{orderNo}/buyer/confirm")
	public String buyerConfirm(HttpServletRequest request, @PathVariable String orderNo) {
		FuwuOrder fuwuOrder = orderService.getFuwuOrder(orderNo);
		int memberId = (int) request.getAttribute("memberId");
		if (fuwuOrder != null) {
			boolean bizResult = orderBizService.buyerConfirmPayment(orderNo, memberId);
			if (bizResult) {
				return String.format("redirect:/order/%s/detail", orderNo);
			}
		}
		return "";
	}
}
