package com.gaoding.h5.controller.order;

import com.gaoding.domain.po.order.FuwuOrder;
import com.gaoding.domain.po.order.FuwuOrderPhase;
import com.gaoding.domain.sys.ErrorDef;
import com.gaoding.domain.sys.ResultDef;
import com.gaoding.framework.conf.Config;
import com.gaoding.framework.constant.ErrorConstants;
import com.gaoding.service.order.OrderPayService;
import com.gaoding.service.order.OrderService;
import com.gaoding.service.weixin.WeixinPayService;
import com.gaoding.util.HttpUtil;
import com.gaoding.util.IpUtil;
import com.gaoding.util.UserAgentUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Title: OrderWeixinpayController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月06日 下午8:53 <br>
 *
 * @author Josh Wang
 */
@Controller
public class OrderWeixinpayController {

	private static final String BUILD_QRCODE_URL = Config.getString("build.qrcode.url", "http://soa.renhe.cn/qrcode?text=");

	@Autowired
	private OrderPayService orderPayService;
	@Autowired
	private OrderService orderService;
    @Autowired
	private WeixinPayService weixinPayService;

    //TODO: 支付过程较耗时,走异步比较好
    @ResponseBody
    @RequestMapping(value = "/order/weixinpay", method = RequestMethod.POST)
    public String pay(HttpServletRequest request, @RequestParam String orderNo) {
        String clientIp = IpUtil.getIpAddress(request);
        int memberId = (int) request.getAttribute("memberId");
        String userAgent = request.getHeader("User-Agent");
        FuwuOrder fuwuOrder = orderService.getFuwuOrder(orderNo);
        FuwuOrderPhase fuwuOrderPhase = orderService.getFuwuOrderPhase(fuwuOrder.getOrderNo(), fuwuOrder.getCurrentPhase());

        if (fuwuOrderPhase.getStatus() != FuwuOrderPhase.Status.DEFAULT) {
            return ErrorDef.valueOf(ErrorConstants.ERROR, "订单已支付过, 不能重复支付");
        } else if (UserAgentUtil.isWeixin(userAgent)) {
            String openid = (String) request.getAttribute("openid");
            String prepayId = orderPayService.buildWeixinPayOrder(memberId, openid, orderNo, clientIp);
            if (StringUtils.isNotEmpty(prepayId)) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("prepayId", prepayId);
                return ResultDef.valueOf(jsonObj);
            }
        } else {
            // 扫码支付下单
            String codeUrl = orderPayService.buildWeixinPayOrder(memberId, "", orderNo, clientIp);
            if (StringUtils.isNotEmpty(codeUrl)) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("codeUrl", codeUrl);
                return ResultDef.valueOf(jsonObj);
            }
        }
        return ErrorDef.valueOf(ErrorConstants.ERROR, "订单支付失败");
    }

	/**
	 *  页面跳到此页面进行微信支付
	 * @param request
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/order/weixinpay", method = RequestMethod.GET)
	public String payView(HttpServletRequest request, @RequestParam String orderNo) {
		String userAgent = request.getHeader("User-Agent");
        if (UserAgentUtil.isWeixin(userAgent)) {
            String prepayId = request.getParameter("prepayId");
            if (StringUtils.isNotEmpty(prepayId)) {
                // 页面js调用支付接口
                Map<String, Object> jsapiPayParamMap = weixinPayService.getBrandWCPayRequest(prepayId);
                request.setAttribute("jsapiMap", jsapiPayParamMap);
                request.setAttribute("callWeixinPay", true);
            } else {
                request.setAttribute("errorInfo", "订单支付失败");
            }
		} else {
			// 扫码支付下单
            String codeUrl = request.getParameter("codeUrl");
            System.out.println(codeUrl);
            if (StringUtils.isNotEmpty(codeUrl)) {
				String url = BUILD_QRCODE_URL + codeUrl;
				String qrcodeUrl = HttpUtil.httpGet(url);
				if (StringUtils.isNotEmpty(qrcodeUrl)) {
					request.setAttribute("qrCodeUrl", qrcodeUrl);
				} else {
                    request.setAttribute("errorInfo", "订单支付失败");
                }
			} else {
                request.setAttribute("errorInfo", "订单支付失败");
            }
		}
		return "order/payResultView";
	}

}
