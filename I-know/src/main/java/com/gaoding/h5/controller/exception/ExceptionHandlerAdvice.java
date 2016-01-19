package com.gaoding.h5.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.spinn3r.log5j.Logger;

/**
 * Title: ExceptionHandler.java<br>
 * Description: 全局的错误处理，提醒用户系统出错了<br>
 * @author Totti
*/
@ControllerAdvice
public class ExceptionHandlerAdvice {

	private static final Logger logger = Logger.getLogger();

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleIOException(Exception ex) {
		logger.error("系统抛出了未捕获的异常", ex);
		return "error";
	}
}
