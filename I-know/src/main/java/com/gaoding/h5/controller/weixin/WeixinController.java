package com.gaoding.h5.controller.weixin;

import com.gaoding.domain.vo.weixin.message.MessageBase;
import com.gaoding.service.weixin.WeixinMessageService;
import com.gaoding.service.weixin.util.WeixinXmlMessageConverter;
import com.gaoding.service.weixin.util.WeixinSignatureUtil;
import com.spinn3r.log5j.Logger;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Title: WeixinController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年10月21日 下午7:01 <br>
 *
 * @author Josh Wang
 */
@Controller
@RequestMapping(value = "/weixin/index")
public class WeixinController {

	private static final Logger logger = Logger.getLogger();

	@Autowired
	private WeixinMessageService weixinMessageService;

	/**
	 *  首次URL接入认证接口
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String handleIndexGetRequest(HttpServletRequest request) {

		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		if (WeixinSignatureUtil.check(signature, timestamp, nonce)) {
			return echostr;
		}
        return StringUtils.EMPTY;
	}

	/**
	 *  接受用户在服务号中发送的消息
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String handleIndexPostRequest(HttpServletRequest request) {

		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(request.getInputStream());
			MessageBase requestMessage = WeixinXmlMessageConverter.toMessage(document);
			if (requestMessage != null) {
				MessageBase responseMessage = weixinMessageService.dispatchMessage(requestMessage);
				if (responseMessage != null) {
					System.out.println(WeixinXmlMessageConverter.toXml(responseMessage, "xml").asXML());
					return WeixinXmlMessageConverter.toXml(responseMessage, "xml").asXML();
				}
			}
		} catch (Exception e) {
			logger.error("receive message error", e);
		}
		return null;
	}
}
