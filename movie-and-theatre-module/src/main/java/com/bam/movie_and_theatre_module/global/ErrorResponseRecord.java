package com.bam.movie_and_theatre_module.global;

import java.time.LocalDateTime;

public record ErrorResponseRecord(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {}
