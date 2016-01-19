package com.gaoding.h5.controller.member;

import com.gaoding.domain.po.member.Member;
import com.gaoding.domain.sys.ErrorDef;
import com.gaoding.service.member.MemberService;
import com.gaoding.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Title: MemberInfoController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月12日 上午10:34 <br>
 *
 * @author Josh Wang
 */
@Controller
public class MemberProfileController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/member/profile", method = RequestMethod.GET)
    public String profile(HttpServletRequest request) {
        return "member/profile";
    }

    @ResponseBody
    @RequestMapping(value = "/member/save/profile", method = RequestMethod.POST)
    public String saveProfile(HttpServletRequest request) {
        int memberId = (int) request.getAttribute("memberId");
        String name = request.getParameter("name");
        String company = request.getParameter("company");
        String title = request.getParameter("title");
        int workAge = NumberUtil.toInt(request.getParameter("work_age"));
        int prov = NumberUtil.toInt(request.getParameter("prov"));
        int city = NumberUtil.toInt(request.getParameter("city"));
        int industry = NumberUtil.toInt(request.getParameter("industry"));
        // 更新用户信息
        memberService.updateMember(memberId, name, company, title, prov, city, industry, workAge);
        // 默认返回成功
        return ErrorDef.SUCCESS.toJsonString();
    }
}
