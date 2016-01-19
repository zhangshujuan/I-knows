package com.gaoding.h5.controller.order;

import com.gaoding.domain.po.fuwu.Fuwu;
import com.gaoding.domain.po.fuwu.FuwuPhase;
import com.gaoding.domain.po.member.Member;
import com.gaoding.domain.po.order.FuwuOrder;
import com.gaoding.domain.po.order.OrderDemand;
import com.gaoding.domain.sys.ErrorDef;
import com.gaoding.framework.constant.ErrorConstants;
import com.gaoding.service.fuwu.FuwuService;
import com.gaoding.service.member.MemberService;
import com.gaoding.service.order.OrderBizService;
import com.gaoding.service.order.OrderDemandService;
import com.gaoding.service.order.OrderService;
import com.gaoding.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Title: BuildOrderController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月04日 下午2:07 <br>
 *
 * @author Josh Wang
 */
@Controller
public class BuildOrderController {

	@Autowired
	private OrderDemandService orderDemandService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private FuwuService fuwuService;
	@Autowired
	private OrderBizService orderBizService;
	@Autowired
	private MemberService memberService;

	/**
	 *  填写资料页面
	 * @param request
	 * @param fuwuId
	 * @return
	 */
	@RequestMapping(value = "/order/fuwu/{fuwuId}/buy", method = RequestMethod.GET)
	public String fillInfoView(HttpServletRequest request, @PathVariable int fuwuId) {
		// fuwuId 有效性判断
		// TODO:
		// 创建订单号
		String orderNo = DateUtil.currentOrderText() + RandomUtil.getRandomNumText(6);
		Member member = (Member) request.getAttribute("member");
		// 生成需求买家信息
		orderDemandService.insertOrderMemberInfo(orderNo, fuwuId, member);
		return String.format("redirect:/order/%s/generate", orderNo);
	}

	/**
	 *  用户点击支付按钮,先调用生成订单
	 * @param request
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/order/{orderNo}/generate", method = RequestMethod.GET)
	public String buildOrderView(HttpServletRequest request, @PathVariable String orderNo) {
		OrderDemand orderDemand = orderDemandService.getOrderDemand(orderNo);
		FuwuOrder fuwuOrder = orderService.getFuwuOrder(orderNo);
		// 订单已存在
		if (fuwuOrder != null) {
			return String.format("redirect:/order/%s/detail", orderNo);
		}
		// 获取服务及阶段信息
		int fuwuId = orderDemand.getFuwuId();
		Fuwu fuwu = fuwuService.getFuwuById(fuwuId);
		Member seller = memberService.getMemberById(fuwu.getSellerId());
		List<FuwuPhase> fuwuPhaseList = fuwuService.getFuwuPhaseList(fuwuId);
		request.setAttribute("fuwu", fuwu);
		request.setAttribute("seller", seller);
		request.setAttribute("fuwuPhaseList", fuwuPhaseList);
		// TODO
		request.setAttribute("orderNo", orderNo);
		return "order/generateView";
	}

	/**
	 *  生成订单
	 * @param request
	 * @param orderNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/order/{orderNo}/generate", method = RequestMethod.POST)
	public String post(HttpServletRequest request, @PathVariable String orderNo) {
		boolean allPayment = NumberUtil.toBoolean(request.getParameter("allpay"));
		int memberId = (int) request.getAttribute("memberId");
		String demand = request.getParameter("demand");
		// 更新订单需求
		orderDemandService.updateOrderDemand(orderNo, memberId, demand);
		// 创建订单
		int resultCode = orderBizService.buildOrder(orderNo, memberId, allPayment);
		if (resultCode == ErrorConstants.SUCCESS) {
			return ErrorDef.SUCCESS.toJsonString();
		} else if (resultCode == ErrorConstants.ORDER_HAS_EXIST) {
			return ErrorDef.valueOf(resultCode, "订单已存在");
		} else if (resultCode == ErrorConstants.FUWU_IS_YOURS) {
			return ErrorDef.valueOf(resultCode, "不能购买自己的产品");
		} else {
			return ErrorDef.valueOf(ErrorConstants.ERROR, "生成订单错误");
		}
	}

}
