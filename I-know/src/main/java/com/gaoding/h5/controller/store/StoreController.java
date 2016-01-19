package com.gaoding.h5.controller.store;

import com.gaoding.domain.po.member.Member;
import com.gaoding.domain.po.seller.SellerCase;
import com.gaoding.domain.po.seller.SellerInfo;
import com.gaoding.domain.po.seller.SellerTag;
import com.gaoding.domain.po.seller.StoreCollection;
import com.gaoding.domain.sys.ResultDef;
import com.gaoding.domain.vo.seller.SellerInfoVo;
import com.gaoding.framework.constant.ErrorConstants;
import com.gaoding.service.fuwu.FuwuService;
import com.gaoding.service.member.MemberService;
import com.gaoding.service.seller.SellerService;
import com.gaoding.service.seller.StoreCollectionService;
import com.gaoding.util.HtmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: StoreController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月20日 下午1:43 <br>
 *
 * @author Josh Wang
 */
@Controller public class StoreController {

	@Autowired private MemberService memberService;

	@Autowired private SellerService sellerService;

	@Autowired private FuwuService fuwuService;

	@Autowired private StoreCollectionService storeCollectionService;

	@RequestMapping(value = "/store/{storeId}", method = RequestMethod.GET) public String storeView(HttpServletRequest request,
			@PathVariable int storeId) {
		int memberId = -1;
		if (request.getAttribute("memberId") != null) {
			memberId = (int) request.getAttribute("memberId");
		}
		Member sellerMember = memberService.getMemberById(storeId);
		// 用户不存在
		if (sellerMember == null) {
			// TODO 用户不存在
		}
		// 非卖家用户
		if (!memberService.checkMemberIsSeller(storeId)) {
			// TODO 非卖家用户
		}
		request.setAttribute("seller", sellerMember);
		SellerInfoVo sellerInfoVo = sellerService.getSellerInfoVo(storeId);
		request.setAttribute("sellerInfoVo", sellerInfoVo);
		// 店铺提供的服务列表
		request.setAttribute("fuwuItemVoList", fuwuService.getFuwuItemVoListBySeller(storeId));
		// 处理没有链接的店铺成功案例
		List<SellerCase> sellerCaseList = sellerService.getSellerCaseInfo(storeId);
		if (sellerCaseList != null && sellerCaseList.size() > 0) {
			for (SellerCase sellerCase : sellerCaseList) {
				if (StringUtils.isEmpty(sellerCase.getLink())) {
					sellerCase.setLink("/store/case/" + sellerCase.getId());
				}
			}
		}
		request.setAttribute("sellerCaseList", sellerCaseList);
		// 是否已经收藏
		if (memberId > 0) {
			request.setAttribute("isStoreCollected", storeCollectionService.checkStoreCollect(memberId, storeId));
		}
		// 收藏数
		request.setAttribute("collectCount", storeCollectionService.getStoreCollectionCount(storeId));
		// 是否是卖家自己的店铺
		Integer loginMemberId = (Integer) request.getAttribute("memberId");
		if (loginMemberId != null && storeId == loginMemberId.intValue()) {
			request.setAttribute("isSelf", true);
		}
		// seo标题
		StringBuffer titleBuffer = new StringBuffer();
		if (!"全国".equals(sellerInfoVo.getAddressName()) && StringUtils.isNotEmpty(sellerInfoVo.getAddressName())) {
			titleBuffer.append(sellerInfoVo.getAddressName().trim());
		}
		titleBuffer.append(sellerMember.getName()).append("店铺");
		List<SellerTag> sellerTagList = sellerInfoVo.getSellerTagList();
		if (sellerTagList != null && sellerTagList.size() > 0) {
			for (SellerTag sellerTag : sellerTagList) {
				if (StringUtils.isNotEmpty(sellerTag.getTag()) && (titleBuffer.length() + sellerTag.getTag().length()) < 26) {
					titleBuffer.append("-").append(sellerTag.getTag());
				}
			}
		}
		titleBuffer.append("-赞服务");
		request.setAttribute("pageTitle", titleBuffer.toString());
		return "store/detail";
	}

	@ResponseBody
	@RequestMapping(value = "/store/{storeId}/collect")
	public String collect(HttpServletRequest request, @PathVariable int storeId) {
		Integer memberId = (Integer) request.getAttribute("memberId");
		if (memberId == null || memberId <= 0) {
			return ResultDef.valueOf(ErrorConstants.LOGIN_AUTH, "请登录先");
		}
		Member sellerMember = memberService.getMemberById(storeId);
		if (sellerMember == null) {
			return ResultDef.valueOf(ErrorConstants.STORE_NOT_EXISTS, "店铺不存在");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		// 判断是否已收藏
		boolean hasCollect = storeCollectionService.checkStoreCollect(memberId, storeId);
		if (hasCollect) {
			storeCollectionService.removeStoreCollection(memberId, storeId);
			result.put("collectStatus", StoreCollection.Status.UNVALID.getValue());
		} else {
			storeCollectionService.insertStoreCollection(memberId, storeId);
			result.put("collectStatus", StoreCollection.Status.VALID.getValue());
		}
		result.put("collectCount", storeCollectionService.getStoreCollectionCount(storeId));
		return ResultDef.valueOf(result);
	}

	@RequestMapping(value = "store/my/try")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		Integer memberId = (Integer) request.getAttribute("memberId");
		SellerInfo sellerInfo = sellerService.getSellerInfo(memberId);
		if (sellerInfo == null) {
			// 不是卖家
			return "store/try";
		} else {
			// 已经是卖家，跳转到店铺页面
			return "redirect:/store/" + memberId;
		}
	}
}