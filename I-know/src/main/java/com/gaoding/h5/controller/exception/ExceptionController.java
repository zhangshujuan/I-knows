package com.gaoding.h5.controller.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController {

	@RequestMapping(value = "/error")
	public String error() {
		return "error";
	}
}
