package com.project.NewsFeed.service;

import com.project.NewsFeed.entity.Event;

import com.project.NewsFeed.repository.EventRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public void createEvent(String title, String description, MultipartFile photo, String link) throws IOException {
        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setLink(link);
        event.setDate(Calendar.getInstance());

        // Save the photo to a specific directory
        String photoFileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
        String photoDirectory = "C:\\Projects\\NewsFeed\\src\\main\\resources\\images\\";
        String photoPath = photoDirectory + UUID.randomUUID() + "_" + photoFileName;

        Files.copy(photo.getInputStream(), Paths.get(photoPath), StandardCopyOption.REPLACE_EXISTING);

        // Set the photo path in the event
        event.setPhotoPath(photoPath);

        eventRepository.save(event);
    }


    public Resource getPhotoAsResource(Long id) throws IOException {
        Event event = eventRepository.findById(id).orElse(null);
        if (event != null) {
            Path photoPath = Paths.get(event.getPhotoPath());
            Resource resource = new ByteArrayResource(Files.readAllBytes(photoPath));
            return resource;
        }
        return null;
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public List<Map<String, Object>> getAllEventsWithPhotoUrls() {
        List<Event> allEvents = eventRepository.findAll();

        if (allEvents != null && !allEvents.isEmpty()) {
            List<Map<String, Object>> responseList = new ArrayList<>();

            for (Event event : allEvents) {
                String photoUrl = "/downloadPhoto/image/" + event.getId(); // URL to download the photo
                Map<String, Object> eventMap = new HashMap<>();
                eventMap.put("photoUrl", photoUrl);
                eventMap.put("id", event.getId());
                eventMap.put("title", event.getTitle());
                eventMap.put("link", event.getLink());
                eventMap.put("description", event.getDescription());
                eventMap.put("photoPath", event.getPhotoPath());
                eventMap.put("date", event.getDate());

                responseList.add(eventMap);
            }

            return responseList;
        } else {
            return Collections.emptyList();
        }
    }

    public String deleteEvent(Long id) {
        eventRepository.deleteById(id);
        return "Event Deleted  ID: "+id;
    }

    public void updateById(Long id, String title, String description, MultipartFile photo, String link) throws IOException {
        // Retrieve the existing event by ID
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        // Update the event with the new values
        existingEvent.setTitle(title);
        existingEvent.setDescription(description);
        existingEvent.setLink(link);
        existingEvent.setDate(Calendar.getInstance());

        // Convert and set the new photo if provided
        if (photo != null && !photo.isEmpty()) {
            // Save the new photo to the specified directory
            String photoFileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
            String photoDirectory = "C:\\Projects\\NewsFeed\\src\\main\\resources\\images\\";
            String photoPath = photoDirectory + UUID.randomUUID() + "_" + photoFileName;

            Files.copy(photo.getInputStream(), Paths.get(photoPath), StandardCopyOption.REPLACE_EXISTING);

            // Set the photo path in the event
            existingEvent.setPhotoPath(photoPath);
        }

        // Save the updated event back to the repository
        eventRepository.save(existingEvent);
    }


    public void createAllEvents(List<String> titles, List<String> links, List<String> descriptions, List<MultipartFile> photos) throws IOException {
        List<Event> events = new ArrayList<>();

        for (int i = 0; i < titles.size(); i++) {
            Event event = new Event();
            event.setTitle(titles.get(i));
            event.setDescription(descriptions.get(i));
            event.setLink(links.get(i));
            event.setDate(Calendar.getInstance());

            // Save the photo to a specific directory
            MultipartFile photo = photos.get(i);
            String photoFileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
            String photoDirectory = "C:\\Projects\\NewsFeed\\src\\main\\resources\\images\\";
            String photoPath = photoDirectory + UUID.randomUUID() + "_" + photoFileName;

            Files.copy(photo.getInputStream(), Paths.get(photoPath), StandardCopyOption.REPLACE_EXISTING);

            // Set the photo path in the event
            event.setPhotoPath(photoPath);

            events.add(event);
        }

        eventRepository.saveAll(events);
    }

public void multipleImage(String title, String description, List<MultipartFile> photos, String link) throws IOException {
    Event event = new Event();
    event.setTitle(title);
    event.setDescription(description);
    event.setLink(link);
    event.setDate(Calendar.getInstance());

    List<String> photoPaths = new ArrayList<>();

    String photoDirectory = "C:\\Projects\\NewsFeed\\src\\main\\resources\\images\\";
    Files.createDirectories(Paths.get(photoDirectory));


    for (MultipartFile photo : photos) {
        String photoFileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
        String photoPath = photoDirectory + UUID.randomUUID() + "_" + photoFileName;

        try (OutputStream outputStream = new FileOutputStream(photoPath)) {

            IOUtils.copy(photo.getInputStream(), outputStream);
        }

        photoPaths.add(photoPath);
    }

    // Concatenate photo paths into a single string
    String concatenatedPhotoPaths = String.join(",", photoPaths);

    // Set the concatenated photo paths in the event
    event.setPhotoPath(concatenatedPhotoPaths);

    // Assuming eventRepository is an instance of JpaRepository or a similar interface
    eventRepository.save(event);
}


//    public List<String> getPhotoPathsById(Long id) {
//        String concatenatedPaths = eventRepository.findPhotoPathsById(id);
//        return Arrays.asList(concatenatedPaths.split(",");
//    }






}





