package com.example.gateway.controller;

import com.example.gateway.model.Event;
import com.example.gateway.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/events")
    public List<Event> Events() {

        List<Event> events = eventService.findAll();

        return events;
    }
}
