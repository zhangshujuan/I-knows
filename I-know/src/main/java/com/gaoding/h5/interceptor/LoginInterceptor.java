package com.gaoding.h5.interceptor;

import com.gaoding.domain.po.member.Member;
import com.gaoding.domain.sys.ErrorDef;
import com.gaoding.framework.constant.ErrorConstants;
import com.gaoding.util.EncoderUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * Title: LoginInterceptor.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年10月21日 下午5:34 <br>
 *
 * @author Josh Wang
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Integer memberId = (Integer) request.getAttribute("memberId");
		Member member = (Member) request.getAttribute("member");
		if (memberId == null || memberId <= 0 || member == null) {
			if (isAjaxRequest(handler)) {
                // ajax 返回登录错误码
				String ajaxErrorResult = ErrorDef.valueOf(ErrorConstants.LOGIN_AUTH, "login auth.");
				response.getWriter().write(ajaxErrorResult);
			} else {
                // 页面请求直接跳转到登陆页
				response.sendRedirect(request.getContextPath() + "/login?goto=" + getCurrentUrl(request));
			}
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	private String getCurrentUrl(HttpServletRequest request) {
		String query = request.getQueryString();
		String currentUrl = "";
		if (StringUtils.isEmpty(query)) {
			currentUrl = EncoderUtil.urlencoder(request.getRequestURI());
		} else {
			currentUrl = EncoderUtil.urlencoder(request.getRequestURI() + "?" + request.getQueryString());
		}
		return currentUrl;
	}

	private boolean isAjaxRequest(Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Annotation[] annotations = handlerMethod.getMethod().getDeclaredAnnotations();
		for (Annotation annotation : annotations) {
			// 根据方法是否含有ResponseBody注解来判断是否为ajax请求
			if (ResponseBody.class.getName().equals(annotation.annotationType().getName())) {
				return true;
			}
		}
		return false;
	}
}
