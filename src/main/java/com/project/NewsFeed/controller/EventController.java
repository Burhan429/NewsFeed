package com.project.NewsFeed.controller;

import com.project.NewsFeed.entity.Event;
import com.project.NewsFeed.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/api/Event")
public class EventController {
    @Autowired
    private EventService eventService;
    @PostMapping("/createEvent")
    public ResponseEntity<String> createEvent(@RequestParam("title") String title,
                                              @RequestParam("link") String link,
                                              @RequestParam("description") String description,
                                              @RequestParam("photo") List<MultipartFile> photo) throws IOException {
        eventService.createEvent(title, description, photo, link);
        return ResponseEntity.ok("Event added successfully");
    }

    @GetMapping("/listOfEvents")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> entityList = eventService.getAllEvents();
        return ResponseEntity.ok(entityList);
    }
    @GetMapping("/getEvent/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id){
       Event getEvent= eventService.getEvent(id);
       return ResponseEntity.ok(getEvent);
    }
    @PutMapping("/updateEvent/{id}")
    public ResponseEntity<String> updateEvent(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String link,
            @RequestParam("photo") List<MultipartFile> photo) throws IOException {

            eventService.updatedEvent(id, title, description, link, photo);
            return ResponseEntity.ok("Event Updated Successfully_______id : " +id);

    }
    @DeleteMapping("/deleteEvent/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event Deleted Successfully_______id : " +id);
    }

}
