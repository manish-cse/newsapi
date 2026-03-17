package com.manishpanwar.newapi.service;

import com.manishpanwar.newapi.dto.QueryAnalysis;
import org.springframework.stereotype.Service;

@Service
public class LLMService {
    
    public QueryAnalysis analyzeQuery(String query) {

        if(query.toLowerCase().contains("technology"))
            return new QueryAnalysis("category","Technology",null,null);

        if(query.toLowerCase().contains("near"))
            return new QueryAnalysis("nearby",null,null,null);

        return new QueryAnalysis("search",null,null,null);
    }

    public String summarize(String title, String description) {
        return "Summary: " + title;
    }
}
