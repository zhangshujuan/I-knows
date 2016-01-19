package com.gaoding.h5.interceptor;

import com.gaoding.domain.po.weixin.WeixinMember;
import com.gaoding.domain.vo.weixin.jsapi.WeixinJSData;
import com.gaoding.service.weixin.WeixinConfig;
import com.gaoding.service.weixin.WeixinService;
import com.gaoding.util.CookieUtil;
import com.gaoding.util.DateUtil;
import com.gaoding.util.UserAgentUtil;
import com.gaoding.service.weixin.util.WeixinOAuthUtil;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: WeixinInterceptor.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年10月27日 下午12:04 <br>
 *
 * @author Josh Wang
 */
public class WeixinInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private WeixinService weixinService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String userAgent = request.getHeader("User-Agent");
		if (UserAgentUtil.isWeixin(userAgent)) {
			String openid = CookieUtil.getWeixinOpenid(request);
			String code = request.getParameter("code");
			// 包含code,即为微信回调,不是很严谨,依据refer比较好
			if (StringUtils.isNotEmpty(code)) {
				JSONObject obj = WeixinOAuthUtil.getOAuthAccessToken(code);
				openid = (String) obj.get("openid");
				if (StringUtils.isNotEmpty(openid)) {
					// 将openid存入cookie
					CookieUtil.saveWeixinOpenid(response, openid);
					// save openid
					weixinService.insertWeixinMember(openid, 0);
				}
			} else if (StringUtils.isEmpty(openid)) {
				// 获取微信openid
				String url = WeixinOAuthUtil.getOAuthBaseUrl(getCurrentUrl(request));
				response.sendRedirect(url);
				return false;
			}
			if (StringUtils.isNotEmpty(openid)) {
				WeixinMember weixinMember = weixinService.getWeixinMember(openid);
				if (weixinMember != null) {
					request.setAttribute("memberId", weixinMember.getMemberId());
					request.setAttribute("openid", openid);
				}
			}
            // 初始化jsapi参数需要
            initWeixinJsData(request);
		}
		return super.preHandle(request, response, handler);
	}

	private String getCurrentUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
        // 由于外层nginx的反向代理, 可能获得url与用户所见并不同,导致微信分享错误
        if (url.endsWith("index")) {
            url = url.substring(0, url.length() - 5);
        }
		String params = request.getQueryString();
		if (StringUtils.isNotEmpty(params)) {
			url += '?' + params;
		}
		return url;
	}

    /**
     *  构建微信jsapi需要参数
     * @param request
     */
	private void initWeixinJsData(HttpServletRequest request) {
		int currentTime = DateUtil.currentTimeInt();
		String currentUrl = getCurrentUrl(request);
		String signature = getSignature(currentTime, currentUrl);

		WeixinJSData weixinJSData = new WeixinJSData();
		weixinJSData.setAppId(WeixinConfig.APPID);
		weixinJSData.setNonceStr(WeixinConfig.NONCESTR);
		weixinJSData.setShareUrl(currentUrl);
		weixinJSData.setTimestamp(currentTime);
		weixinJSData.setSignature(signature);

		request.setAttribute("weixinJSData", weixinJSData);
	}

	private String getSignature(int timestamp, String url) {
		String jsapiTicket = weixinService.getJsapiTicket();
		String keyValue = String.format("jsapi_ticket=%s&noncestr=%s&timestamp=%d&url=%s", jsapiTicket, WeixinConfig.NONCESTR, timestamp, url);
		return DigestUtils.shaHex(keyValue);
	}
}
