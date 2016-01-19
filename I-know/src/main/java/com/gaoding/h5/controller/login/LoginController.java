package com.gaoding.h5.controller.login;

import com.gaoding.domain.po.member.Member;
import com.gaoding.domain.sys.ErrorDef;
import com.gaoding.framework.constant.ErrorConstants;
import com.gaoding.service.member.MemberService;
import com.gaoding.service.member.MemberSessionService;
import com.gaoding.service.member.VerifyCodeService;
import com.gaoding.service.weixin.WeixinService;
import com.gaoding.util.CookieUtil;
import com.gaoding.util.IpUtil;
import com.gaoding.util.MobileUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: LoginController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月04日 下午3:51 <br>
 *
 * @author Josh Wang
 */
@Controller
public class LoginController {

    @Autowired
    private VerifyCodeService verifyCodeService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberSessionService memberSessionService;
    @Autowired
    private WeixinService weixinService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginView(HttpServletRequest request) {
        Integer memberId = (Integer) request.getAttribute("memberId");
        Member member = null;
        if(memberId != null) {
            member = memberService.getMemberById(memberId);
        }
        String gotoUrl = request.getParameter("goto");
        if (memberId != null && memberId > 0 && member != null) {
            return "redirect:" + (StringUtils.isNotBlank(gotoUrl) ? gotoUrl : "/index");
        }
        gotoUrl = StringUtils.isNotBlank(gotoUrl) ? gotoUrl : "/index";
        request.setAttribute("gotoUrl", gotoUrl);
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String mobile = request.getParameter("mobile");
        String code = request.getParameter("verifyCode");

        if (StringUtils.isEmpty(mobile)) {
            return ErrorDef.valueOf(ErrorConstants.MOBILE_EMPTY, "手机号不能为空");
        } else if (StringUtils.isEmpty(code)) {
            return ErrorDef.valueOf(ErrorConstants.VERIFY_CODE_EMPTY, "验证码不能为空");
        } else if (!MobileUtil.isMobile(mobile)) {
            return ErrorDef.valueOf(ErrorConstants.MOBILE_ERROR, "手机号格式错误");
        } else {
            boolean checkCode = verifyCodeService.checkVerifyCode(mobile, code);
            if (checkCode) {
                Member member = memberService.getMemberByMobile(mobile);
                int memberId;
                if (member == null) {
                    memberId = memberService.insertMember(mobile);
                } else {
                    memberId = member.getId();
                }
                // 生成sessionId
                String sessionId = CookieUtil.createSessionId(memberId);
                CookieUtil.saveSessionId(response, sessionId);
                // 本地存储sessionId
                memberSessionService.saveSessionId(sessionId, memberId);
                // openid存在,建立与memberId的关联
                String openid = (String) request.getAttribute("openid");
                if (StringUtils.isNotEmpty(openid)) {
                    weixinService.insertWeixinMember(openid, memberId);
                    memberService.updateWeixinUserInfoToMember(openid, memberId);
                }
                return ErrorDef.SUCCESS.toJsonString();
            } else {
                return ErrorDef.valueOf(ErrorConstants.VERIFY_CODE_ERROR, "验证码错误");
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/ajax/send/code")
    public String sendVerificationCode(HttpServletRequest request) {
        String mobile = request.getParameter("mobile");
        String deviceInfo = request.getHeader("User-Agent");
        String ip = IpUtil.getIpAddress(request);
        // 生成并发送验证码
        String errorMsg = verifyCodeService.genrateVerifyCode(mobile, deviceInfo, ip);
        if (StringUtils.isBlank(errorMsg)) {
            return ErrorDef.SUCCESS.toJsonString();
        } else {
            // 生成验证码失败或者发送次数过多
            return ErrorDef.valueOf(ErrorConstants.ERROR, errorMsg);
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = CookieUtil.getSessionId(request);
        // 清除sessionId cookie
        CookieUtil.deleteGaodingCookie(response);
        CookieUtil.deleteWeixinCookie(response);
        // 清除本地缓存数据
        memberSessionService.deleteSessionId(sessionId);
        // 微信下退出,清除openid和member的关系
        String openid = (String) request.getAttribute("openid");
        if (StringUtils.isNotEmpty(openid)) {
            weixinService.insertWeixinMember(openid, 0);
        }
        return "redirect:/index";
    }
}
