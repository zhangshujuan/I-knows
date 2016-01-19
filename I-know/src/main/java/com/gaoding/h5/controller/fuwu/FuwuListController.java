package com.gaoding.h5.controller.fuwu;

import com.gaoding.domain.vo.fuwu.FuwuItemVo;
import com.gaoding.service.fuwu.FuwuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Title: FuwuListController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月25日 下午4:58 <br>
 *
 * @author Josh Wang
 */
@Controller
public class FuwuListController {

	@Autowired
	private FuwuService fuwuService;

	/**
	 *  精选服务分类列表
	 * @return
	 */
	@RequestMapping(value = "/fuwu/industry/{industryId}")
	public String fuwuList(HttpServletRequest request, @PathVariable int industryId) {
        if (industryId < 1 || industryId > 8) {
            industryId = 8;
        }
        // 8为其他,获取行业精选
        List<FuwuItemVo> fuwuItemVoList = fuwuService.getFuwuItemVoListByIndustry(industryId);
        request.setAttribute("fuwuItemVoList", fuwuItemVoList);
        request.setAttribute("industryId", industryId);
		return "fuwu/industryFuwuList";
	}
}
