package com.gaoding.h5.controller.order;

import com.gaoding.domain.sys.ErrorDef;
import com.gaoding.framework.constant.ErrorConstants;
import com.gaoding.service.order.OrderBizService;
import com.gaoding.util.NumberUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Title: OrderCommentController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月23日 下午4:54 <br>
 *
 * @author Josh Wang
 */
@Controller
public class OrderCommentController {

    @Autowired
    private OrderBizService orderBizService;

	@RequestMapping(value = "/order/{orderNo}/comment", method = RequestMethod.GET)
	public String commentView(HttpServletRequest request, @PathVariable String orderNo) {
        request.setAttribute("orderNo", orderNo);
		return "order/sendComment";
	}

    @ResponseBody
    @RequestMapping(value = "order/{orderNo}/comment", method = RequestMethod.POST)
    public String comment(HttpServletRequest request, @PathVariable String orderNo) {
        int memberId = (int) request.getAttribute("memberId");
        String content = request.getParameter("content");
        int score = NumberUtil.toInt(request.getParameter("score"));
        score = score > 5 && score < 1 ? 1 : score;
        // 评论并置订单状态已评论
        boolean resultFlag = orderBizService.sendOrderComment(orderNo, memberId, content, score);
        if (resultFlag) {
            return ErrorDef.SUCCESS.toJsonString();
        } else {
            return ErrorDef.valueOf(ErrorConstants.ERROR, "评论失败");
        }
    }
}
