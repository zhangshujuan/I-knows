package com.gaoding.h5.controller.member;

import com.gaoding.domain.po.member.MemberTradeInfo;
import com.gaoding.domain.vo.member.MemberTradeInfoVo;
import com.gaoding.service.member.MemberAccountService;
import com.gaoding.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * Title: MemberAccountController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月10日 下午2:42 <br>
 *
 * @author Josh Wang
 */
@Controller
public class MemberAccountController {

    private static int DEFAULT_PAGE = 20;

    @Autowired
    private MemberAccountService memberAccountService;

    @RequestMapping(value = "/member/balance")
    public String balance(HttpServletRequest request) {
        int memberId = (int) request.getAttribute("memberId");
        BigDecimal memberBalance = memberAccountService.getMemberBalance(memberId);
        request.setAttribute("balanceValue", NumberUtil.toString(memberBalance));
        return "member/balance";
    }

    @RequestMapping(value = "/member/balance/detail")
    public String balanceList(HttpServletRequest request, @RequestParam(defaultValue = "1") int page) {
        int memberId = (int) request.getAttribute("memberId");
        List<MemberTradeInfoVo> memberTradeInfoVoList = memberAccountService.getAllMemberTradeList(memberId, page, DEFAULT_PAGE);
        request.setAttribute("tradeInfoVoList", memberTradeInfoVoList);
        return "member/balanceDetail";
    }

    public String tradeList() {
        return "";
    }
}
