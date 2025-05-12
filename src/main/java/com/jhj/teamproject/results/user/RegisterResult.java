package com.jhj.teamproject.results.user;

public enum RegisterResult {
    FAILURE,                        // 이런저런 이유로 실패
    FAILURE_EMAIL_NOT_AVAILABLE,    // 이메일 중복
    FAILURE_NAME_NOT_AVAILABLE, // 닉네임 중복
    SUCCESS,
}
