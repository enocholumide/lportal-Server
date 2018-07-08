package com.enocholumide.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter @Setter @NoArgsConstructor
public class ErrorDetails {
    private Instant timestamp;
    private String message;
    private String details;

    public ErrorDetails(Instant timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
