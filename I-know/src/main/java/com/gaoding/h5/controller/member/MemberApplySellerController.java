package com.gaoding.h5.controller.member;

import com.gaoding.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Title: MemberApplySellerController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月12日 下午5:30 <br>
 *
 * @author Josh Wang
 */
@Controller
public class MemberApplySellerController {

	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/member/apply/seller")
	public String applySeller(HttpServletRequest request) {
		return "member/applySeller";
	}
}
