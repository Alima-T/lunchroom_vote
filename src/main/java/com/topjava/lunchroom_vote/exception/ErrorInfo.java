package com.topjava.lunchroom_vote.exception;

import lombok.Getter;

/**
 * @Alima-T 12/25/2022
 */
@Getter
public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String[] details;

    public ErrorInfo(CharSequence url, ErrorType type, String...details) {
        this.url = url.toString();
        this.type = type;
        this.details = details;
    }
}