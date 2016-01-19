package com.gaoding.h5.controller.order;

import com.gaoding.domain.vo.order.FuwuOrderDetailVo;
import com.gaoding.framework.constant.Constants;
import com.gaoding.service.member.MemberService;
import com.gaoding.service.order.OrderService;
import com.gaoding.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: OrderListController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月05日 上午10:24 <br>
 *
 * @author Josh Wang
 */
@Controller
public class OrderListController {

    private static final int RUNNING_ORDER = 1;
    private static final int FINISHED_ORDER = 2;

	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/order/list")
	public String list(HttpServletRequest request) {
		int memberId = (int) request.getAttribute("memberId");
		boolean isSeller = memberService.checkMemberIsSeller(memberId);
		if (isSeller) {
			return "redirect:/order/seller/list";
		}
		return "redirect:/order/buyer/list";
	}

	@RequestMapping(value = "/order/buyer/list")
	public String buyerList(HttpServletRequest request) {
		int memberId = (int) request.getAttribute("memberId");
		int page = NumberUtil.toInt(request.getParameter("page"));
		page = page < 1 ? 1 : page;
        // 根据type值来判别订单列表类型
        int type = NumberUtil.toInt(request.getParameter("type"));
        List<FuwuOrderDetailVo> fuwuOrderDetailVoList = new ArrayList<FuwuOrderDetailVo>();
        if (type == RUNNING_ORDER) {
            fuwuOrderDetailVoList = orderService.getRunningFuwuOrderList(memberId, page, Constants.DEFAULT_PAGESIZE);
        } else if (type == FINISHED_ORDER) {
            fuwuOrderDetailVoList = orderService.getFinishFuwuOrderList(memberId, page, Constants.DEFAULT_PAGESIZE);
        } else {
            fuwuOrderDetailVoList = orderService.getAllFuwuOrderList(memberId, page, Constants.DEFAULT_PAGESIZE);
            type = 0;
        }
        request.setAttribute("orderType", type);
		request.setAttribute("orderDetailList", fuwuOrderDetailVoList);
		return "order/buyerList";
	}

	@RequestMapping(value = "/order/seller/list")
	public String sellerlist(HttpServletRequest request) {
		int memberId = (int) request.getAttribute("memberId");
        int page = NumberUtil.toInt(request.getParameter("page"));
        page = page < 1 ? 1 : page;
        // 根据type值来判别订单列表类型
        int type = NumberUtil.toInt(request.getParameter("type"));
        List<FuwuOrderDetailVo> fuwuOrderDetailVoList = new ArrayList<FuwuOrderDetailVo>();
        if (type == RUNNING_ORDER) {
            fuwuOrderDetailVoList = orderService.getRunningSellerOrderList(memberId, page, Constants.DEFAULT_PAGESIZE);
        } else if (type == FINISHED_ORDER) {
            fuwuOrderDetailVoList = orderService.getFinishSellerOrderList(memberId, page, Constants.DEFAULT_PAGESIZE);
        } else {
            fuwuOrderDetailVoList = orderService.getAllSellerOrderList(memberId, page, Constants.DEFAULT_PAGESIZE);
            type = 0;
        }
        request.setAttribute("orderType", type);
		request.setAttribute("orderDetailList", fuwuOrderDetailVoList);
		return "order/sellerList";
	}
}
