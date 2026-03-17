package com.manishpanwar.newapi.service;

import com.manishpanwar.newapi.dto.ArticleResponse;
import com.manishpanwar.newapi.model.Article;
import com.manishpanwar.newapi.repository.ArticleRepository;
import com.manishpanwar.newapi.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrendingService.class);
    private final ArticleRepository articleRepository;
    private final SummaryCacheService summaryCacheService;
    private final SummaryGenerationService summaryGenerationService;

    public NewsService(ArticleRepository articleRepository,
                       SummaryCacheService summaryCacheService,
                       SummaryGenerationService summaryGenerationService) {
        this.articleRepository = articleRepository;
        this.summaryCacheService = summaryCacheService;
        this.summaryGenerationService = summaryGenerationService;
    }

    public List<ArticleResponse> category(String category) {
        List<Article> articles = articleRepository.findByCategory(category)
                .stream().limit(5).toList();
        summaryGenerationService.generate(articles);
        return map(articles);
    }

    public List<ArticleResponse> search(String query) {
        List<Article> articles = articleRepository.findAll().stream()
                .filter(a -> a.getTitle().toLowerCase().contains(query.toLowerCase()))
                .limit(5).toList();
        summaryGenerationService.generate(articles);
        return map(articles);
    }

    public List<ArticleResponse> nearby(double lat, double lon, double radius) {
        List<Article> articles = articleRepository.findAll().stream()
                .filter(a -> Utils.calculateDistance(
                        lat, lon, a.getLatitude(), a.getLongitude()) <= radius)
                .limit(5).toList();
        summaryGenerationService.generate(articles);
        return map(articles);
    }

    private List<ArticleResponse> map(List<Article> articles) {
        return articles.stream().map(a -> {
            String summary = summaryCacheService.get(a.getId());
            if(summary == null) summary = "Generating...";
            LOGGER.debug("Summary not ready yet for article {}", a.getId());
            return new ArticleResponse(
                    a.getTitle(),
                    a.getDescription(),
                    a.getUrl(),
                    a.getPublicationDate(),
                    a.getSourceName(),
                    a.getCategory(),
                    a.getRelevanceScore(),
                    summary,
                    a.getLatitude(),
                    a.getLongitude()
            );

        }).toList();
    }
}
