package com.project.NewsFeed.controller;

import com.project.NewsFeed.entity.FutureProgram;
import com.project.NewsFeed.repository.FutureProgramRepository;
import com.project.NewsFeed.service.FutureProgramService;
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
public class FutureProgramController {
    @Autowired
    FutureProgramService programService;
    @Autowired
    FutureProgramRepository programRepository;

    @PostMapping("/createProgram")
    public ResponseEntity<String> createEvent(@RequestParam("title") String title,
                                              @RequestParam("link") String link,
                                              @RequestParam("description") String description,
                                              @RequestParam("photo") MultipartFile photo) throws IOException {
        programService.addProgram(title, description, photo, link);
        return ResponseEntity.ok("Newsfeed item added successfully");
    }

    @GetMapping("/getProgram/{id}")
    public ResponseEntity<Map<String, Object>> getEvent (@PathVariable Long id) throws IOException {
        FutureProgram futureProgram = programService.getProgramById(id);
        Resource resource = programService.getPhotoAsResource(id);

        if (futureProgram != null && resource != null) {
            String photoUrl = "/downloadProgramPhoto/image/" + id; // URL to download the photo

            Map<String, Object> response = new HashMap<>();
            response.put("photoUrl", photoUrl);
            response.put("id", futureProgram.getId());
            response.put("title", futureProgram.getTitle());
            response.put("link", futureProgram.getLink());
            response.put("description", futureProgram.getDescription());
            response.put("photoPath", futureProgram.getPhotoPath());
            response.put("date", futureProgram.getDate());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/downloadProgramPhoto/image/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id) throws IOException {
        Resource resource = programService.getPhotoAsResource(id);

        if (resource != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getAllPrograms")
    public ResponseEntity<List<Map<String, Object>>> getAllPrograms() {
        List<Map<String, Object>> programWithPhotoUrls = programService.getAllFutureProgramsWithPhotoUrls();

        if (!programWithPhotoUrls.isEmpty()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(programWithPhotoUrls);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/updateProgram/{id}")
    public ResponseEntity<Map<String, Object>> updateProgram(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam MultipartFile photo,
            @RequestParam String link) {
        try {
            programService.updateById(id, title, description, photo, link);

            // Fetch the updated Program
            FutureProgram updatedProgram = programService.getProgramById(id);

            if (updatedProgram != null) {
                String photoUrl = "/downloadPhoto/image/" + id; // URL to download the updated photo

                Map<String, Object> response = new HashMap<>();
                response.put("photoUrl", photoUrl);
                response.put("id", updatedProgram.getId());
                response.put("title", updatedProgram.getTitle());
                response.put("link", updatedProgram.getLink());
                response.put("description", updatedProgram.getDescription());
                response.put("photoPath", updatedProgram.getPhotoPath());
                response.put("date", updatedProgram.getDate());

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


}
