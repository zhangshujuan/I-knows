package com.gaoding.h5.controller.common.ajax;

import com.gaoding.domain.po.common.Address;
import com.gaoding.service.common.AddressService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: AddressController.class<br>
 * Description: <br>
 * Copyright (c) 人和网版权所有 2015    <br>
 * Create DateTime: 2015年11月17日 下午3:00 <br>
 *
 * @author Josh Wang
 */
@Controller
public class AddressController {

	@Autowired
	private AddressService addressService;

	private static String addressJson;

	@ResponseBody
	@RequestMapping(value = "/ajax/address", method = RequestMethod.GET)
	public String allAddress() {
		if (StringUtils.isEmpty(addressJson)) {
			addressJson = getAddressJson();
		}
		return addressJson;
	}

	private String getAddressJson() {
		JSONArray root = new JSONArray();
		List<Address> provList = addressService.getProvList();
		for (Address prov : provList) {
			JSONObject proJson = new JSONObject();
			proJson.put("n", prov.getName());
			proJson.put("v", prov.getId());
			List<Address> cityList = addressService.getCityList(prov.getId());
			JSONArray cityJsonArray = new JSONArray();
			for (Address city : cityList) {
				JSONObject cityJson = new JSONObject();
				cityJson.put("n", city.getName());
				cityJson.put("v", city.getId());
				cityJsonArray.add(cityJson);
			}
			proJson.put("s", cityJsonArray);
			root.add(proJson);
		}
		return root.toString();
	}
}
