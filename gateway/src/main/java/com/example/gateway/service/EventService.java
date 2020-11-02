package com.example.gateway.service;

import com.example.gateway.model.Event;
import com.example.gateway.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;


    public List<Event> findAll() {

        return eventRepository.findAll();
    }


}
