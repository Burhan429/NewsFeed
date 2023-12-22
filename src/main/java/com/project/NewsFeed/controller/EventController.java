package com.project.NewsFeed.controller;

import com.project.NewsFeed.entity.Event;
import com.project.NewsFeed.entity.Notification;
import com.project.NewsFeed.model.EventRequest;
import com.project.NewsFeed.model.EventResponse;
import com.project.NewsFeed.model.NotificationRequest;
import com.project.NewsFeed.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;
    @PostMapping("/createEvent")
    public String createEvent(
            @ModelAttribute EventRequest eventRequest,
            @RequestParam("eventImage") MultipartFile file,
            @RequestParam("eventTitle") String eventTitle,
            @RequestParam("eventLink") String eventLink,
            @RequestParam("eventDescription") String eventDescription
    )
    {
        eventRequest.setEventId(UUID.randomUUID().toString());
        eventRequest.setEventImage(file);
        return eventService.createEvent(eventRequest);
    }
    @GetMapping("/getEventById/{eventId}")
    public Event getEventById(@PathVariable String eventId) {
        return eventService.getEventById(eventId);

    }
    @GetMapping("/getAllEvents")
    public List<Event> getAllNotify(){
        return eventService.getAllEvents();
    }
    @DeleteMapping("/deleteByEventId/{eventId}")
    public String deleteEvent(@PathVariable String eventId){
        return eventService.deleteEvent(eventId);
    }


}
