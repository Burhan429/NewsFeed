package com.project.NewsFeed.service;

import com.project.NewsFeed.entity.Event;
import com.project.NewsFeed.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public void createEvent(String title, String description, List<MultipartFile> photos, String link) throws IOException {
        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setLink(link);
        event.setDate(Calendar.getInstance());

        List<String> photoPaths = new ArrayList<>();

        for (MultipartFile photo : photos)
        {
            String photoFileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
            String photoDirectory = "C:\\Projects\\NewsFeed\\src\\main\\resources\\images\\";
            String photoPath = photoDirectory + UUID.randomUUID() + "_" + photoFileName;

            Files.copy(photo.getInputStream(), Paths.get(photoPath), StandardCopyOption.REPLACE_EXISTING);
            photoPaths.add(photoPath);      //Add the photo path to the list
        }
        event.setPhotoPath(photoPaths);    //Set the list of photo paths in the event
        eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElse(null);
    }


    public void updatedEvent(Long id, String title, String description, String link, List<MultipartFile> photos) throws IOException {
        Event existingEvent = eventRepository.findById(id).get();
        existingEvent.setTitle(title);
        existingEvent.setDescription(description);
        existingEvent.setLink(link);
        List<String> photoPaths = new ArrayList<>();

        for (MultipartFile photo : photos) {

            String photoFileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
            String photoDirectory = "C:\\Projects\\NewsFeed\\src\\main\\resources\\images\\";
            String photoPath = photoDirectory + UUID.randomUUID() + "_" + photoFileName;

            Files.copy(photo.getInputStream(), Paths.get(photoPath), StandardCopyOption.REPLACE_EXISTING);
            photoPaths.add(photoPath);
        }
         existingEvent.setPhotoPath(photoPaths);
         eventRepository.save(existingEvent);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

}





