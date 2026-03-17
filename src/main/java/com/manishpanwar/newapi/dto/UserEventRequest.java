package com.manishpanwar.newapi.dto;

public record UserEventRequest(String userId,
                               String articleId,
                               String eventType,
                               double latitude,
                               double longitude) {
}
