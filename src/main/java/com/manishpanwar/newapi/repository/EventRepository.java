package com.manishpanwar.newapi.repository;

import com.manishpanwar.newapi.model.UserEvent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class EventRepository {
    private final List<UserEvent> userEvents = new CopyOnWriteArrayList<>();

    public void save(UserEvent event) {
        userEvents.add(event);
    }

    public List<UserEvent> findAll() {
        return userEvents;
    }
}
