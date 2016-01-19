package com.gaoding.h5.interceptor;

import com.gaoding.framework.constant.DomainConstants;
import com.gaoding.service.member.MemberService;
import com.gaoding.service.member.MemberSessionService;
import com.gaoding.util.CookieUtil;
import com.gaoding.util.velocity.VMUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: WebInterceptor.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年10月21日 下午5:32 <br>
 *
 * @author Josh Wang
 */
public class WebInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private MemberSessionService memberSessionService;
	@Autowired
	private MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String sessionId = CookieUtil.getSessionId(request);
		int sessionMemberId = CookieUtil.getSessionMemberId(sessionId);
		// 第三方绑定账号,如微信
		Integer otheremberId = (Integer) request.getAttribute("memberId");
		if (otheremberId == null || otheremberId <= 0) {
			// cookie sessionId验证
			if (StringUtils.isNotEmpty(sessionId) && sessionMemberId > 0) {
				// 服务端验证
				int memberId = memberSessionService.getSessionMemberId(sessionId);
				if (memberId > 0 && memberId == sessionMemberId) {
					// 验证通过
					request.setAttribute("memberId", memberId);
					request.setAttribute("member", memberService.getMemberById(memberId));
				}
			}
		} else {
            request.setAttribute("member", memberService.getMemberById(otheremberId));
        }
		putCommonValues(request);
		return super.preHandle(request, response, handler);
	}

	// 设置公用的变量
	private void putCommonValues(HttpServletRequest request) {
        request.setAttribute("vmUtil", VMUtil.getVMUtil());

		request.setAttribute("staticDomain", DomainConstants.STATIC_DOMAIN);
		request.setAttribute("staticVersion", DomainConstants.STATIC_VERSION);
		request.setAttribute("imageHttp", String.format("http://%s", DomainConstants.STATIC_DOMAIN));
		request.setAttribute("staticHttp", String.format("http://%s/%s", DomainConstants.STATIC_DOMAIN, DomainConstants.STATIC_VERSION));
	}
}
