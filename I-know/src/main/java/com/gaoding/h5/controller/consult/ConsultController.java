package com.gaoding.h5.controller.consult;

import com.gaoding.domain.sys.ErrorDef;
import com.gaoding.domain.sys.ResultDef;
import com.gaoding.domain.vo.consult.ConsultVo;
import com.gaoding.framework.constant.ErrorConstants;
import com.gaoding.service.member.MemberService;
import com.gaoding.service.seller.ConsultService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: ConsultController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年12月18日 下午5:16 <br>
 *
 * @author podongfeng
 */
@Controller
public class ConsultController {

    @Autowired
    private ConsultService consultService;

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "consult/{consultId}")
    public String detail(HttpServletRequest request, HttpServletResponse response, @PathVariable int consultId) {
        int memberId = (int) request.getAttribute("memberId");
        request.setAttribute("consultDetailVo", consultService.getConsultDetailVoByConsultId(consultId));
        request.setAttribute("selfId", memberId);
        return "consult/detail";
    }

    // TODO nginx配置好跳转之后删除, 这个是为了兼容之前已经发在微信提醒消息和短信提醒消息中的链接
    @RequestMapping(value = "consult/seller")
    public String seller(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/consult/list";
    }

    @RequestMapping(value = "consult/list")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        Integer memberId = (Integer) request.getAttribute("memberId");
        request.setAttribute("consultVoList", consultService.getConsultVoListByMemberId(memberId));
        request.setAttribute("selfId", memberId);
        return "consult/list";
    }

    @ResponseBody
    @RequestMapping(value = "/consult/reply/{consultId}", method = RequestMethod.POST)
    public String reply(HttpServletRequest request, @PathVariable int consultId, @RequestParam(value="content", required=true) String content) {
        int memberId = (int) request.getAttribute("memberId");
        ConsultVo consultVo = consultService.getConsultVoById(consultId);
        if (consultVo == null) {
            return ErrorDef.valueOf(ErrorConstants.ERROR, "会话不存在");
        }
        int toId = -1;
        if (consultVo.getConsult().getSellerId() == memberId) {
            toId = consultVo.getConsult().getMemberId();
        } else {
            toId = consultVo.getConsult().getSellerId();
        }
        if (memberId <= 0) {
            return ErrorDef.valueOf(ErrorConstants.ERROR, "用户不存在");
        } else if (StringUtils.isEmpty(content)) {
            return ErrorDef.valueOf(ErrorConstants.ERROR, "内容不能为空");
        } else {
            consultService.replyConsult(consultId, memberId, toId, content);
            boolean isFollowWeixin = memberService.isFollowWeixin(memberId);
            return new ResultDef(isFollowWeixin).toJsonString();
        }
    }
}
