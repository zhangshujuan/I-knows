package com.gaoding.h5.controller.member;

import com.gaoding.domain.po.fuwu.Fuwu;
import com.gaoding.domain.po.member.Member;
import com.gaoding.domain.vo.fuwu.FuwuItemVo;
import com.gaoding.framework.constant.Constants;
import com.gaoding.service.fuwu.FuwuCollectionService;
import com.gaoding.service.seller.StoreCollectionService;
import com.gaoding.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Title: MemberCollectionController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月26日 下午1:19 <br>
 *
 * @author Josh Wang
 */
@Controller
public class MemberCollectionController {

    @Autowired
    private FuwuCollectionService fuwuCollectionService;
    @Autowired
    private StoreCollectionService storeCollectionService;

    @RequestMapping(value = "/fuwu/collection", method = RequestMethod.GET)
    public String collectionFuwuList(HttpServletRequest request) {
        int memberId = (int) request.getAttribute("memberId");
        int page = NumberUtil.toInt(request.getParameter("page"));
        List<FuwuItemVo> fuwuItemVoList = fuwuCollectionService.getMemberCollectionFuwuItemVoList(memberId, page, Constants.DEFAULT_PAGESIZE);
        request.setAttribute("fuwuItemVoList", fuwuItemVoList);
        return "member/fuwuCollection";
    }

    @RequestMapping(value = "/store/collection", method = RequestMethod.GET)
    public String collectionStoreList(HttpServletRequest request) {
        int page = NumberUtil.toInt(request.getParameter("page"));
        int memberId = (int) request.getAttribute("memberId");
        List<Member> sellerList = storeCollectionService.getCollectStoreList(memberId, page, Constants.DEFAULT_PAGESIZE);
        request.setAttribute("sellerList", sellerList);
        return "member/storeCollection";
    }
}
