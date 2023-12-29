package com.project.NewsFeed.controller;

import com.project.NewsFeed.entity.Event;
import com.project.NewsFeed.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class EventController {
    @Autowired
    private EventService eventService;
    @PostMapping("/createEvent")
    public ResponseEntity<String> createEvent(@RequestParam("title") String title,
                                              @RequestParam("link") String link,
                                              @RequestParam("description") String description,
                                              @RequestParam("photo") MultipartFile photo) throws IOException {
        eventService.createEvent(title, description, photo, link);
        return ResponseEntity.ok("Newsfeed item added successfully");
    }
    @PostMapping("/multipleImage")
    public ResponseEntity<String> createEvent(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("photos") List<MultipartFile> photos,
            @RequestParam("link") String link) throws IOException {


            eventService.multipleImage(title, description, photos, link);
            return  ResponseEntity.ok("Multiple Images created ");

    }


    @PostMapping("/saveAllEvents")
    public ResponseEntity<String> saveAllEvents (@RequestParam("title") List<String> title,
                                                @RequestParam("link") List<String> link,
                                                @RequestParam("description") List<String> description,
                                                @RequestParam("photo") List<MultipartFile> photo) throws IOException {
        eventService.createAllEvents(title,link,description,photo);
        return ResponseEntity.ok("All Events are added");
    }

    @GetMapping("/getEvent/{id}")
    public ResponseEntity<Map<String, Object>> getEvent (@PathVariable Long id) throws IOException {
        Event event = eventService.getEventById(id);
        Resource resource = eventService.getPhotoAsResource(id);

        if (event != null && resource != null) {

            String photoUrl = "/downloadPhoto/image/" + id; // URL to download the photo
            Map<String, Object> response = new HashMap<>();
            response.put("photoUrl", photoUrl);
            response.put("id", event.getId());
            response.put("title", event.getTitle());
            response.put("link", event.getLink());
            response.put("description", event.getDescription());
            response.put("photoPath", event.getPhotoPath());
            response.put("date", event.getDate());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/downloadPhoto/image/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id) throws IOException {
        Resource resource = eventService.getPhotoAsResource(id);

        if (resource != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllEvents")
    public ResponseEntity<List<Map<String, Object>>> getAllEvents() {
        List<Map<String, Object>> eventsWithPhotoUrls = eventService.getAllEventsWithPhotoUrls();

        if (!eventsWithPhotoUrls.isEmpty()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(eventsWithPhotoUrls);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/deleteByEventId/{id}")
    public String deleteEvent(@PathVariable Long id){
        return eventService.deleteEvent(id);
    }


    @PutMapping("/updateEvent/{id}")
    public ResponseEntity<Map<String, Object>> updateEvent(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam MultipartFile photo,
            @RequestParam String link) {
        try {
            eventService.updateById(id, title, description, photo, link);

            // Fetch the updated event
            Event updatedEvent = eventService.getEventById(id);

            if (updatedEvent != null) {
                String photoUrl = "/downloadPhoto/image/" + id; // URL to download the updated photo

                Map<String, Object> response = new HashMap<>();
                response.put("photoUrl", photoUrl);
                response.put("id", updatedEvent.getId());
                response.put("title", updatedEvent.getTitle());
                response.put("link", updatedEvent.getLink());
                response.put("description", updatedEvent.getDescription());
                response.put("photoPath", updatedEvent.getPhotoPath());
                response.put("date", updatedEvent.getDate());

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    @GetMapping("/photoPaths/{id}")
//    public ResponseEntity<List<String>> getPhotoPaths(@PathVariable Long id) {
//        List<String> photoPaths = eventService.getPhotoPathsById(id);
//        return ResponseEntity.ok(photoPaths);
//
//    }
}
