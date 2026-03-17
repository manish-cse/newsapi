package com.manishpanwar.newapi.controller;

import com.manishpanwar.newapi.dto.ArticleResponse;
import com.manishpanwar.newapi.dto.QueryAnalysis;
import com.manishpanwar.newapi.service.LLMService;
import com.manishpanwar.newapi.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {
    private final NewsService newsService;
    private final LLMService llmService;

    public NewsController(NewsService newsService, LLMService llmService) {
        this.newsService = newsService;
        this.llmService = llmService;
    }

    @GetMapping("/query")
    public List<ArticleResponse> query(@RequestParam String text) {
        QueryAnalysis q = llmService.analyzeQuery(text);
        return switch (q.intent()) {
            case "category" -> newsService.category(q.category());
            case "nearby" ->
                    // Currently using hardcoded location; ideally lat/lon should come from query params
                    newsService.nearby(17.50, 75.50, 500);
            default -> newsService.search(text);
        };
    }
}
