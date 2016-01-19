package com.gaoding.h5.controller.order;

import com.gaoding.domain.po.order.FuwuOrder;
import com.gaoding.domain.sys.ErrorDef;
import com.gaoding.framework.constant.ErrorConstants;
import com.gaoding.service.order.OrderBizService;
import com.gaoding.service.order.OrderService;
import com.gaoding.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: SellerHandleOrderController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月16日 下午8:38 <br>
 *
 * @author Josh Wang
 */
@Controller
public class SellerHandleOrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderBizService orderBizService;

	@RequestMapping(value = "/order/{orderNo}/seller/accept")
	public String acceptOrder(HttpServletRequest request, @PathVariable String orderNo) {
		int memberId = (int) request.getAttribute("memberId");
		FuwuOrder fuwuOrder = orderService.getFuwuOrder(orderNo);
		if (fuwuOrder != null && fuwuOrder.getSellerId() == memberId) {
			boolean bizResult = orderBizService.acceptOrder(orderNo, memberId);
			if (bizResult) {
				return String.format("redirect:/order/%s/detail?showAcceptMsg=true", orderNo);
			}
		}
		return "";
	}

	@RequestMapping(value = "/order/{orderNo}/seller/refuse")
	public String refuseOrder(HttpServletRequest request, @PathVariable String orderNo) {
		int memberId = (int) request.getAttribute("memberId");
		FuwuOrder fuwuOrder = orderService.getFuwuOrder(orderNo);
		if (fuwuOrder != null && fuwuOrder.getSellerId() == memberId) {
			boolean bizResult = orderBizService.refuseOrder(orderNo, memberId);
			if (bizResult) {
				return String.format("redirect:/order/%s/detail", orderNo);
			}
		}
		return "";
	}

	@RequestMapping(value = "/order/{orderNo}/seller/complete")
	public String completeOrderPhase(HttpServletRequest request, @PathVariable String orderNo) {
		int memberId = (int) request.getAttribute("memberId");
		FuwuOrder fuwuOrder = orderService.getFuwuOrder(orderNo);
		if (fuwuOrder != null && fuwuOrder.getSellerId() == memberId) {
			boolean bizResult = orderBizService.sellerCompletePhase(orderNo, memberId);
			if (bizResult) {
				return String.format("redirect:/order/%s/detail", orderNo);
			}
		}
		return "";
	}

	@ResponseBody
	@RequestMapping(value = "/order/{orderNo}/change/price", method = RequestMethod.POST)
	public String changePrice(HttpServletRequest request, @PathVariable String orderNo) {
		int memberId = (int) request.getAttribute("memberId");
		String priceText = request.getParameter("newPrice");
		BigDecimal newPrice = new BigDecimal(priceText);
		// 订单阶段价格修改
		int resultCode = orderBizService.modifyPrice(orderNo, newPrice, memberId);
		if (resultCode == ErrorConstants.SUCCESS) {
			return ErrorDef.SUCCESS.toJsonString();
		} else if (resultCode == ErrorConstants.MODIFY_PRICE_EGT_NOW) {
			return ErrorDef.valueOf(resultCode, "修改价格必须小于当前价格");
		} else {
			return ErrorDef.valueOf(ErrorConstants.ERROR, "修改价格失败");
		}
	}

    @ResponseBody
    @RequestMapping(value = "/order/{orderNo}/change/prices", method = RequestMethod.POST)
    public String changePrices(HttpServletRequest request, @PathVariable String orderNo) {
        int memberId = (int) request.getAttribute("memberId");
        String priceText = request.getParameter("newPrice");
        String[] pricesText = priceText.split(",");
        List<BigDecimal> newPriceList = new ArrayList<>();
        for (String price : pricesText) {
            newPriceList.add(new BigDecimal(price));
        }

        int resultCode = orderBizService.sellerAdjustOrder(orderNo, memberId, newPriceList.toArray(new BigDecimal[0]));
        if (resultCode == ErrorConstants.SUCCESS) {
            return ErrorDef.SUCCESS.toJsonString();
        } else if (resultCode == ErrorConstants.MODIFY_PRICE_EGT_NOW) {
            return ErrorDef.valueOf(resultCode, "已付款阶段修改价格必须小于当前价格");
        } else {
            return ErrorDef.valueOf(ErrorConstants.ERROR, "修改价格失败");
        }
    }
}
