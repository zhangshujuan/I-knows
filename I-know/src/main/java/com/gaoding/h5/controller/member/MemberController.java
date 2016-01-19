package com.gaoding.h5.controller.member;

import com.gaoding.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Title: MemberController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月20日 上午11:25 <br>
 *
 * @author Josh Wang
 */
@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public String meView(HttpServletRequest request) {
        int memberId = (int) request.getAttribute("memberId");
        boolean isSeller = memberService.checkMemberIsSeller(memberId);
        request.setAttribute("isSeller", isSeller);
        return "member/me";
    }
}
