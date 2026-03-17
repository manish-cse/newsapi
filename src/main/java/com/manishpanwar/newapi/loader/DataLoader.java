package com.manishpanwar.newapi.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.manishpanwar.newapi.model.Article;
import com.manishpanwar.newapi.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);
    private final ArticleRepository articleRepository;

    public DataLoader(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("------Starting DataLoader-----");

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();

        InputStream is = getClass().getResourceAsStream("/news_data.json");
        if (is == null) {
            LOGGER.error("news_data.json not found");
            throw new RuntimeException("news_data.json not found");
        }
        List<Article> articles = Arrays.asList(mapper.readValue(is, Article[].class));
        articleRepository.saveAll(articles);
        LOGGER.info("Loaded {} articles into repository", articles.size());
    }
}
