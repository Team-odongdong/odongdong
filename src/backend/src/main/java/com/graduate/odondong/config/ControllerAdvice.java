package com.graduate.odondong.config;

import static com.graduate.odondong.util.ErrorLogWriter.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;
import com.graduate.odondong.util.BaseResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(BaseException.class)
	public BaseResponse<String> exceptionHandler(BaseException exception, HttpServletRequest request) {
		writeExceptionWithRequest(exception, request);
		return new BaseResponse<>(BaseResponseStatus.SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public BaseResponse<String> exceptionHandler(Exception exception, HttpServletRequest request) {

		writeExceptionWithAopRequest(exception, request);

		log.error(Arrays.stream(exception.getStackTrace())
			.map(StackTraceElement::toString)
			.map(toString -> toString + "\n")
			.collect(Collectors.joining()));
		return new BaseResponse<>(BaseResponseStatus.SERVER_ERROR);
	}
}
