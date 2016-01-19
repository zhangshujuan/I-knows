package com.gaoding.h5.controller.store;

import com.gaoding.domain.po.fuwu.Fuwu;
import com.gaoding.domain.po.member.Member;
import com.gaoding.domain.sys.ErrorDef;
import com.gaoding.domain.sys.ResultDef;
import com.gaoding.framework.constant.ErrorConstants;
import com.gaoding.service.fuwu.FuwuService;
import com.gaoding.service.member.MemberService;
import com.gaoding.service.seller.ConsultService;
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
 * Title: AskController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月20日 下午6:53 <br>
 *
 * @author Josh Wang
 */
@Controller
public class AskController {

    @Autowired
    private ConsultService consultService;
    @Autowired
    private FuwuService fuwuService;
    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/seller/{sellerId}/ask", method = RequestMethod.GET)
    public String askView(HttpServletRequest request, @PathVariable int sellerId) {
        String fuwuIdText = request.getParameter("fuwuId");
        int fuwuId = NumberUtil.toInt(fuwuIdText);
        if (fuwuId > 0) {
            Fuwu fuwu = fuwuService.getFuwuById(fuwuId);
            request.setAttribute("fuwu", fuwu);
        }
        Member seller = memberService.getMemberById(sellerId);
        request.setAttribute("seller", seller);
        request.setAttribute("sellerId", sellerId);
        return "store/askView";
    }

    @ResponseBody
    @RequestMapping(value = "/seller/{sellerId}/ask", method = RequestMethod.POST)
    public String ask(HttpServletRequest request, @PathVariable int sellerId) {
        int memberId = (int) request.getAttribute("memberId");
        String content = request.getParameter("content");
        if (memberId <= 0 || sellerId <= 0) {
            return ErrorDef.valueOf(ErrorConstants.ERROR, "用户不存在");
        } else if (StringUtils.isEmpty(content)) {
            return ErrorDef.valueOf(ErrorConstants.ERROR, "内容不能为空");
        } else {
            consultService.insertConsult(sellerId, memberId, content);
            boolean isFollowWeixin = memberService.isFollowWeixin(memberId);
            return new ResultDef(isFollowWeixin).toJsonString();
        }
    }
}
