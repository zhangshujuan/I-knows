package com.gaoding.h5.controller.order;

import com.gaoding.domain.po.fuwu.Comment;
import com.gaoding.domain.po.member.Member;
import com.gaoding.domain.po.order.FuwuOrder;
import com.gaoding.domain.vo.order.FuwuOrderDetailVo;
import com.gaoding.domain.vo.order.OrderDemandVo;
import com.gaoding.domain.vo.order.OrderProcessRecordVo;
import com.gaoding.service.fuwu.FuwuService;
import com.gaoding.service.member.MemberService;
import com.gaoding.service.order.OrderDemandService;
import com.gaoding.service.order.OrderProcessService;
import com.gaoding.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Title: OrderDetailController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月05日 上午10:14 <br>
 *
 * @author Josh Wang
 */
@Controller
public class OrderDetailController {

	@Autowired
	private OrderService orderService;
    @Autowired
    private FuwuService fuwuService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private OrderDemandService orderDemandService;
    @Autowired
    private OrderProcessService orderProcessService;

	@RequestMapping(value = "/order/{orderNo}/detail", method = RequestMethod.GET)
	public String orderDetail(HttpServletRequest request, @PathVariable String orderNo, @RequestParam(value="false", required=false) boolean showAcceptMsg) {
		if (showAcceptMsg) {
			request.setAttribute("showAcceptMsg", showAcceptMsg);
		}
		int memberId = (int) request.getAttribute("memberId");
		Member member = memberService.getMemberById(memberId);
        // 订单详细,包含阶段信息
		FuwuOrderDetailVo fuwuOrderDetailVo = orderService.getFuwuOrderDetailVo(orderNo);
		FuwuOrder fuwuOrder = fuwuOrderDetailVo.getFuwuOrder();
        // 订单过程信息
		List<OrderProcessRecordVo> orderProcessRecordVoList = orderProcessService.getOrderProcessRecordVoList(orderNo);
        // 订单需求信息
        OrderDemandVo orderDemandVo = orderDemandService.getOrderDemandVo(orderNo);

        request.setAttribute("member", member);
		request.setAttribute("orderDetail", fuwuOrderDetailVo);
        request.setAttribute("orderProcessRecordList", orderProcessRecordVoList);
        request.setAttribute("orderDemandVo", orderDemandVo);
        // 订单到已评论阶段
        if (fuwuOrder.getStatus() == FuwuOrder.Status.COMMENTED) {
            List<Comment> commentList = fuwuService.getMemberOrderCommentList(fuwuOrder.getOrderNo());
            request.setAttribute("commentList", commentList);
        }
		if (memberId == fuwuOrder.getMemberId()) {
			// 买家
			return "order/buyerDetail";
		} else if (memberId == fuwuOrder.getSellerId()) {
			// 卖家
			return "order/sellerDetail";
		} else {
			return "";
		}
	}
}
