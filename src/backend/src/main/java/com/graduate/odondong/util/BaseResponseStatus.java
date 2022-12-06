package com.graduate.odondong.util;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Bathroom 오류
     */
    NOT_FOUND_BATHROOM(false, 2000, "없는 화장실 입니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),

    /**
     * 5000 : AWS관련 오류
     */
    AWS_ACCESS_DENIED(false,5001 ,"접근 권한이 없습니다."),
    AWS_FILE_NOT_FOUND(false,5002 ,"파일 키에 해당하는 파일이 존재하지 않습니다."),
    AWS_FILE_CONVERT_FAIL(false, 5003, "파일 변환에 실패했습니다."),

    //6000
    GEOCODING_ERROR(false, 6000, "도로명 주소 변환에 실패했습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
