package com.gaoding.h5.controller.fuwu;

import com.gaoding.domain.po.fuwu.FuwuCase;
import com.gaoding.service.fuwu.FuwuService;
import com.gaoding.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Title: FuwuCaseController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年12月01日 下午2:39 <br>
 *
 * @author podongfeng
 */
@Controller
public class FuwuCaseController {

    @Autowired
    private FuwuService fuwuService;

    @RequestMapping(value = "fuwu/case/{fuwuCaseId}")
    public String detail(ModelMap modelMap, @PathVariable int fuwuCaseId) {
        FuwuCase fuwuCase = fuwuService.getFuwuCaseById(fuwuCaseId);
        fuwuCase.setDescription(HtmlUtil.line2br(fuwuCase.getDescription()));
        modelMap.put("fuwuCase", fuwuCase);
        return "fuwu/fuwuCase";
    }
}
