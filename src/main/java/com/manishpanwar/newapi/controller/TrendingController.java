package com.manishpanwar.newapi.controller;

import com.manishpanwar.newapi.model.Article;
import com.manishpanwar.newapi.service.TrendingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class TrendingController {
    private final TrendingService trendingService;

    public TrendingController(TrendingService trendingService) {
        this.trendingService = trendingService;
    }

    @GetMapping("/trending")
    public List<Article> trending(@RequestParam(defaultValue = "5") int limit) {
        return trendingService.getTrending(limit);
    }
}
