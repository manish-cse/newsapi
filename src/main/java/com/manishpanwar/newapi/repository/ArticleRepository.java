package com.manishpanwar.newapi.repository;

import com.manishpanwar.newapi.model.Article;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ArticleRepository {
    private final Map<String, Article> newsDataStore = new ConcurrentHashMap<>();

    public void saveAll(List<Article> articles) {
        for (Article a : articles) {
            newsDataStore.put(a.getId(), a);
        }
    }

    public List<Article> findAll() {
        return new ArrayList<>(newsDataStore.values());
    }

    public Optional<Article> findById(String id) {
        return Optional.ofNullable(newsDataStore.get(id));
    }

    public List<Article> findByCategory(String category) {
        return newsDataStore.values().stream()
                .filter(article -> article.getCategory() != null &&
                        article.getCategory().stream()
                                .anyMatch(c -> c.equalsIgnoreCase(category)))
                .sorted(Comparator.comparing(Article::getPublicationDate).reversed())
                .toList();
    }
}
