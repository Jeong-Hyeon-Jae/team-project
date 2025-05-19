package com.jhj.annual.user.results;

public enum RegisterResult implements Result {
    SUCCESS,
    FAILURE,
    FAILURE_INVALID_EMAIL,
    FAILURE_DUPLICATE,
    FAILURE_INVALID_PASSWORD,
    FAILURE_INVALID_NAME
}
