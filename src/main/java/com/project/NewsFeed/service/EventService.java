package com.project.NewsFeed.service;

import com.project.NewsFeed.entity.Event;

import com.project.NewsFeed.entity.Notification;
import com.project.NewsFeed.model.EventRequest;
import com.project.NewsFeed.model.EventResponse;
import com.project.NewsFeed.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public String createEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setEventId(eventRequest.getEventId());
        event.setEventTitle(eventRequest.getEventTitle());
        event.setEventLink(eventRequest.getEventLink());
        event.setEventDescription(eventRequest.getEventDescription());

        MultipartFile file = eventRequest.getEventImage();
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String uniqueFileName = UUID.randomUUID() + "." + fileExtension;
        event.setEventImage(uniqueFileName);

        String base64EventImage = Base64.getEncoder().encodeToString(originalFileName.getBytes());
        event.setEventImage(base64EventImage);

        event.setCreatedDate(Calendar.getInstance());
        eventRepository.save(event);
        return "Event Saved";
    }



    public Event getEventById(String eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);

        if (event != null) {
            EventResponse eventResponse = new EventResponse();

            byte[] imageBytes = Base64.getDecoder().decode(event.getEventImage());
            String encodedImage = new String(Base64.getEncoder().encode(imageBytes), StandardCharsets.UTF_8);

            eventResponse.setEventImage(encodedImage);
            eventResponse.setEventId(event.getEventId());
            eventResponse.setEventLink(event.getEventLink());
            eventResponse.setEventTitle(event.getEventTitle());
            eventResponse.setEventDescription(event.getEventDescription());

            return event;
        } else {
            // Handle the case where the event is not found
            return null;
        }
    }





    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }


    public String deleteEvent(String eventId) {
        eventRepository.deleteById(eventId);
        return "Event Deleted  ID: "+eventId;
    }
}