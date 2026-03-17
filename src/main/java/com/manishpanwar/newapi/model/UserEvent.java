package com.manishpanwar.newapi.model;

import java.time.LocalDateTime;

public record UserEvent(String userId,
                        String articleId,
                        String eventType,
                        LocalDateTime timestamp,
                        double latitude,
                        double longitude) {
}
