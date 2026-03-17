package com.manishpanwar.newapi.service;

import com.manishpanwar.newapi.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryGenerationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SummaryGenerationService.class);

    private final LLMService llmService;
    private final SummaryCacheService summaryCacheService;

    public SummaryGenerationService(LLMService llmService,
                                    SummaryCacheService summaryCacheService) {
        this.llmService = llmService;
        this.summaryCacheService = summaryCacheService;
    }

    @Async
    public void generate(List<Article> articles) {
        LOGGER.info("Generating summaries for {} articles", articles.size());
        for (Article a : articles) {
            summaryCacheService.computeIfAbsent(a.getId(), id -> {
                LOGGER.info("Computing summary for article {}", id);
                return llmService.summarize(a.getTitle(), a.getDescription());
            });
        }
    }
}
