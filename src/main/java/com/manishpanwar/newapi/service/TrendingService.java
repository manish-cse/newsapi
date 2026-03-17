package com.manishpanwar.newapi.service;

import com.manishpanwar.newapi.model.Article;
import com.manishpanwar.newapi.model.UserEvent;
import com.manishpanwar.newapi.repository.ArticleRepository;
import com.manishpanwar.newapi.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TrendingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrendingService.class);
    private final EventRepository eventRepository;
    private final ArticleRepository articleRepository;

    public TrendingService(EventRepository eventRepository,
                           ArticleRepository articleRepository) {
        this.eventRepository = eventRepository;
        this.articleRepository = articleRepository;
    }

    public List<Article> getTrending(int limit) {
        Map<String,Integer> score = new HashMap<>();
        for(UserEvent e : eventRepository.findAll()) {
            int weight = e.eventType().equals("CLICK") ? 3 : 1;
            score.put(e.articleId(), score.getOrDefault(e.articleId(),0)+weight);
        }
        LOGGER.info("Computed trending scores for {} articles", score.size());
        return score.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
                .limit(limit)
                .map(e -> articleRepository.findById(e.getKey()).orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }
}
