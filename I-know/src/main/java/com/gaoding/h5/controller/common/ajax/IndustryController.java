package com.gaoding.h5.controller.common.ajax;

import com.gaoding.domain.po.common.Industry;
import com.gaoding.service.common.IndustryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Title: IndustryController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月17日 下午3:01 <br>
 *
 * @author Josh Wang
 */
@Controller
public class IndustryController {

    @Autowired
    private IndustryService industryService;

    private static String industryJson;

    @ResponseBody
    @RequestMapping(value = "/ajax/industry", method = RequestMethod.GET)
    public String allIndustry() {
        if (StringUtils.isEmpty(industryJson)) {
            industryJson = getIndustryJson();
        }
        return industryJson;
    }

    private String getIndustryJson() {
        List<Industry> industryList = industryService.getIndustryList();
        JSONArray root = new JSONArray();
        for (Industry industry : industryList) {
            JSONObject industryJson = new JSONObject();
            industryJson.put("n", industry.getName());
            industryJson.put("v", industry.getId());
            root.add(industryJson);
        }
        return root.toString();
    }
}
