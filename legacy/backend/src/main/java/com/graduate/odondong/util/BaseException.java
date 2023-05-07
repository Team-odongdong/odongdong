package com.graduate.odondong.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends IllegalArgumentException {
    private BaseResponseStatus status;
}