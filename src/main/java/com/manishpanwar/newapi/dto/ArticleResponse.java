package com.manishpanwar.newapi.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleResponse(String title,
                              String description,
                              String url,
                              LocalDateTime publicationDate,
                              String sourceName,
                              List<String> category,
                              double relevanceScore,
                              String llmSummary,
                              double latitude,
                              double longitude) {
}
