package com.gaoding.h5.controller.store;

import com.gaoding.domain.po.seller.SellerCase;
import com.gaoding.service.seller.SellerService;
import com.gaoding.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Title: SellerCaseController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年12月01日 下午2:54 <br>
 *
 * @author podongfeng
 */
@Controller
public class SellerCaseController {

    @Autowired
    private SellerService sellerService;

    @RequestMapping(value = "store/case/{sellerCaseId}")
    public String detail(ModelMap modelMap, @PathVariable int sellerCaseId) {
        SellerCase sellerCase = sellerService.getSellerCaseInfoById(sellerCaseId);
        sellerCase.setDescription(HtmlUtil.line2br(sellerCase.getDescription()));
        modelMap.put("sellerCase", sellerCase);
        return "store/sellerCase";
    }
}