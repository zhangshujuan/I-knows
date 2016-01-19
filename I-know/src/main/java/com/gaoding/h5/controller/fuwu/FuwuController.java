package com.gaoding.h5.controller.fuwu;

import com.gaoding.domain.po.fuwu.Banner;
import com.gaoding.domain.po.fuwu.Fuwu;
import com.gaoding.domain.po.fuwu.FuwuCase;
import com.gaoding.domain.po.fuwu.FuwuPhase;
import com.gaoding.domain.vo.fuwu.FuwuDetailVo;
import com.gaoding.domain.vo.fuwu.FuwuItemVo;
import com.gaoding.framework.constant.DomainConstants;
import com.gaoding.service.fuwu.FuwuCollectionService;
import com.gaoding.service.fuwu.FuwuService;
import com.gaoding.util.HtmlUtil;
import com.gaoding.util.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Title: FuwuController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月06日 18:30 <br>
 *
 * @author podongfeng
 */
@Controller
public class FuwuController {

	@Autowired
	private FuwuService fuwuService;
	@Autowired
	private FuwuCollectionService fuwuCollectionService;

	@RequestMapping(value = "index")
	public String index(ModelMap modelMap) {
		List<Banner> bannerList = fuwuService.getBannerList();
		List<FuwuItemVo> fuwuItemVoList = fuwuService.getHotFuwuItemVoListByIndustry(0);
		modelMap.put("bannerList", bannerList);
		modelMap.put("fuwuItemVoList", fuwuItemVoList);
		modelMap.put("bannerDomain", DomainConstants.BANNER_DOMAIN);
		return "fuwu/index";
	}

	@RequestMapping(value = "fuwu/{fuwuId}/detail")
	public String detailDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable int fuwuId) {
		return detail(request, response, fuwuId);
	}

	@RequestMapping(value = "fuwu/{fuwuId}")
	public String detail(HttpServletRequest request, HttpServletResponse response, @PathVariable int fuwuId) {
		Integer memberId = (Integer) request.getAttribute("memberId");
		if (memberId != null && memberId > 0) {
			boolean collect = fuwuCollectionService.checkFuwuCollect(memberId, fuwuId);
			request.setAttribute("hasCollect", collect);
		}
		FuwuDetailVo fuwuDetailVo = fuwuService.getFuwuDetailVoById(fuwuId);

		// 处理没有链接的服务成功案例
		List<FuwuCase> fuwuCaseList = fuwuDetailVo.getFuwuCaseList();
		if (fuwuCaseList != null && fuwuCaseList.size() != 0) {
			for (FuwuCase fuwuCase : fuwuCaseList) {
				if (StringUtils.isEmpty(fuwuCase.getLink())) {
					fuwuCase.setLink("/fuwu/case/" + fuwuCase.getId());
				}
			}
		}
		request.setAttribute("fuwuDetailVo", fuwuDetailVo);
		request.setAttribute("recommendFuwuList", fuwuService.getRecommendFuwuList());
		return "fuwu/detail";
	}
}
