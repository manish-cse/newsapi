package com.manishpanwar.newapi.controller;

import com.manishpanwar.newapi.dto.UserEventRequest;
import com.manishpanwar.newapi.model.UserEvent;
import com.manishpanwar.newapi.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserEventRequest request) {
        UserEvent event = new UserEvent(
                request.userId(),
                request.articleId(),
                request.eventType(),
                LocalDateTime.now(),
                request.latitude(),
                request.longitude()
        );
        eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
