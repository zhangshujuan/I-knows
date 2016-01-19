package com.gaoding.h5.controller.activity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Title: ActivityController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年12月17日 下午1:17 <br>
 *
 * @author podongfeng
 */
@Controller
public class ActivityController {

    @RequestMapping(value = "/qiyefuwu")
    public String index() {
        return "activity/qiyefuwu";
    }
}
