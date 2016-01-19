package com.gaoding.h5.controller.member;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaoding.domain.po.member.AttendMember;
import com.gaoding.domain.sys.ErrorDef;
import com.gaoding.service.member.AttendMemberService;
import com.gaoding.util.MobileUtil;

import net.sf.json.JSONObject;

/**
 * Title: AttendMemberController.java<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月23日 下午3:20:20 <br>
 * @author fengyuxu
 */
@Controller
@RequestMapping(value = "/attend")
public class AttendMemberController {

	@Autowired
	private AttendMemberService attendMemberService;

	@RequestMapping(value = "/index")
	public String index() {
		return "attendmember/attend";
	}

	@RequestMapping(value = "/attend")
	@ResponseBody
	public String attend(@ModelAttribute("fuwuParam") AttendMember attendMember) {
		JSONObject res = new JSONObject();
		// 验证
		if (StringUtils.isEmpty(attendMember.getName())) {
			return ErrorDef.valueOf(-1, "姓名不能为空.");
		} else if (StringUtils.isEmpty(attendMember.getMobile())) {
			return ErrorDef.valueOf(-2, "手机号不能为空.");
		} else if (!(MobileUtil.isMobile(attendMember.getMobile()) || MobileUtil.isTel(attendMember.getMobile()))) {
			return ErrorDef.valueOf(-3, "手机号格式错误.");
		} else if (StringUtils.isNotEmpty(attendMember.getDescription()) && attendMember.getDescription().length() > 800) {
			return ErrorDef.valueOf(-4, "描述不能超过800");
		}
		// 来源为H5
		attendMember.setSource(AttendMember.Source.H5);
		try {
			attendMemberService.insert(attendMember);
			res.put("successFlag", true);
			res.put("errorMsg", "");
		} catch (Exception e) {
			res.put("successFlag", false);
			res.put("errorMsg", "入驻失败");
			e.printStackTrace();
		}
		return res.toString();
	}
}
