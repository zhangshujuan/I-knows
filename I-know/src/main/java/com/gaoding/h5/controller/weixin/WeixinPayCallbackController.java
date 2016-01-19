package com.gaoding.h5.controller.weixin;

import com.gaoding.service.order.OrderPayService;
import com.gaoding.service.weixin.WeixinConfig;
import com.gaoding.service.weixin.WeixinPayService;
import com.gaoding.service.weixin.util.WeixinSignUtil;
import com.spinn3r.log5j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: WeixinPayCallbackController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月03日 下午3:06 <br>
 *
 * @author Josh Wang
 */
@Controller
public class WeixinPayCallbackController {

	private static Logger logger = Logger.getLogger();

    @Autowired
    private WeixinPayService weixinPayService;
    @Autowired
    private OrderPayService orderPayService;

    /**
     *  微信支付完成的回调
     * @param request
     * @param response
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/weixin/pay/callback")
	public String payCallback(HttpServletRequest request, HttpServletResponse response) {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(request.getInputStream());
			String xmlText = document.asXML();
			logger.info("weixin callback.\n" + xmlText);
			Map<String, String> resultMap = WeixinSignUtil.getMapFromXML(xmlText);
			if (!weixinPayService.verifySign(resultMap)) {
				logger.error("微信支付签名认证错误");
			} else if (orderPayService.weixinPayCallback(resultMap)) {
                // TODO 对订单现有状态判断
				// 返回微信成功接收
				Map<String, Object> responseMap = new HashMap<String, Object>();
				responseMap.put("return_code", WeixinConfig.SUCCESS);
				responseMap.put("return_msg", "OK");
                logger.info("weixin pay success.");
				return WeixinSignUtil.getXmlText(responseMap);
			}
		} catch (Exception e) {
			logger.error("callback", e);
		}
		return "";
	}
}
