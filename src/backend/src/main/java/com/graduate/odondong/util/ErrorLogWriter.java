package com.graduate.odondong.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorLogWriter {

    /**
     * jwt 헤더가 포함된 request 로그를 남길 때 user id 정보를 로그에 같이 남긴다.
     * 로그에 request 정보와 그 때의 exception 메세지를 남긴다.
     *
     * @param exception
     * @param request
     */
    public static void writeExceptionWithAuthorizedRequest(Exception exception, HttpServletRequest request) {
        log.error("{} - user id: {} | uri: {} {} | query string: {}", exception.getMessage(), request.getAttribute("user_id").toString(),
                request.getMethod(), request.getRequestURI(), parameterMapToString(request.getParameterMap()));
    }

    /**
     * jwt 헤더가 포함된 request이면서 body값이 있는 요청이라면 로그를 남길 때 user id 정보를 로그에 같이 남긴다.
     * 로그에 request 정보와 그 때의 exception 메세지, body를 남긴다.
     *
     * @param exception
     * @param request
     * @param messageBody
     */
    public static void writeExceptionWithAuthorizedRequest(Exception exception, HttpServletRequest request, String messageBody) {
        log.error("{} - user id: {} | uri: {} {} | query string: {} | body: {}", exception.getMessage(), request.getAttribute("user_id").toString(),
                request.getMethod(), request.getRequestURI(), parameterMapToString(request.getParameterMap()), messageBody);
    }

    /**
     * 로그에 request 정보, exception 메세지를 남긴다.
     * Authorization 헤더가 없는 경우
     * @param exception
     * @param request
     */
    public static void writeExceptionWithRequest(BaseException exception, HttpServletRequest request) {
        log.error("{} clientIp: {} - Authorization: {} | uri: {} {} | query string: {}", exception.getStatus().getMessage(), getClientIp(request), request.getHeader("Authorization"),
                request.getMethod(), request.getRequestURI(), parameterMapToString(request.getParameterMap()));
    }

    /**
     * 로그에 request 정보, exception 메세지를 남긴다.
     * Authorization 헤더가 없는 경우
     * AOP 에러 로그
     * @param exception
     * @param request
     */
    public static void writeExceptionWithAopRequest(Exception exception, HttpServletRequest request) {
        log.error("{} clientIp: {} - Authorization: {} | uri: {} {} | query string: {}", exception.getMessage(), getClientIp(request), request.getHeader("Authorization"),
                request.getMethod(), request.getRequestURI(), parameterMapToString(request.getParameterMap()));
    }

    /**
     * 로그에 request 정보, exception 메세지, body를 남긴다.
     *
     * @param exception
     * @param request
     * @param messageBody
     */
    public static void writeExceptionWithRequest(Exception exception, HttpServletRequest request, String messageBody) {
        log.error("{} - Authorization: {} | uri: {} {} | query string: {} | body: {}", exception.getMessage(), request.getHeader("Authorization"),
                request.getMethod(), request.getRequestURI(), parameterMapToString(request.getParameterMap()), messageBody);
    }

    /**
     * 로그에 메세지와 그 때의 exception 메세지를 남긴다
     * Request가 아닌 에러일때 로그
     * @param exception
     * @param message   로그에 남길 메세지
     */
    public static void writeExceptionWithMessage(Exception exception, String message) {
        log.error("{} - {}", exception.getMessage(), message);
    }

    private static String parameterMapToString(Map<String, String[]> parameters) {
        return parameters.entrySet().stream()
                .map(p -> p.getKey() + "=" + String.join(",", p.getValue()))
                .reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");
    }

    private static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
