package com.gaoding.h5.controller.fuwu;

import com.gaoding.domain.po.fuwu.Fuwu;
import com.gaoding.domain.sys.ErrorDef;
import com.gaoding.framework.constant.ErrorConstants;
import com.gaoding.service.fuwu.FuwuCollectionService;
import com.gaoding.service.fuwu.FuwuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Title: FuwuCollectionController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月20日 下午2:22 <br>
 *
 * @author Josh Wang
 */
@Controller
public class FuwuCollectionController {

	@Autowired
	private FuwuService fuwuService;
	@Autowired
	private FuwuCollectionService fuwuCollectionService;

	@ResponseBody
	@RequestMapping(value = "/fuwu/{fuwuId}/collect", method = RequestMethod.POST)
	public String collect(HttpServletRequest request, @PathVariable int fuwuId) {
		Integer memberId = (Integer) request.getAttribute("memberId");
		if (memberId == null || memberId <= 0) {
			return ErrorDef.valueOf(ErrorConstants.LOGIN_AUTH, "请登录");
		}
		Fuwu fuwu = fuwuService.getFuwuById(fuwuId);
		if (fuwu == null) {
			return ErrorDef.valueOf(ErrorConstants.FUWU_NOT_EXIST, "服务不存在");
		}
		// 判断是否已收藏
		boolean hasCollect = fuwuCollectionService.checkFuwuCollect(memberId, fuwuId);
		if (hasCollect) {
			fuwuCollectionService.removeFuwuCollection(memberId, fuwuId);
			return ErrorDef.valueOf(ErrorConstants.SUCCESS, "已取消收藏");
		} else {
			fuwuCollectionService.insertFuwuCollection(memberId, fuwuId);
			return ErrorDef.valueOf(ErrorConstants.SUCCESS, "已收藏");
		}
	}
}
