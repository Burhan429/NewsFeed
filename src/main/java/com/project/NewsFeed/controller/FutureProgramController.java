package com.project.NewsFeed.controller;

import com.project.NewsFeed.entity.FutureProgram;
import com.project.NewsFeed.repository.FutureProgramRepository;
import com.project.NewsFeed.service.FutureProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.project.NewsFeed.service.FutureProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/FutureProgram")
public class FutureProgramController {
    @Autowired
    private FutureProgramService programService;
    @PostMapping("/createFutureProgram")
    public ResponseEntity<String> createFutureProgram(@RequestParam("title") String title,
                                              @RequestParam("link") String link,
                                              @RequestParam("description") String description,
                                              @RequestParam("photo") List<MultipartFile> photo) throws IOException {
        programService.createFutureProgram(title, description, photo, link);
        return ResponseEntity.ok("Future Program added successfully");
    }

    @GetMapping("/listOfFuturePrograms")
    public ResponseEntity<List<FutureProgram>> getAllFuturePrograms() {
        List<FutureProgram> FutureProgramList = programService.getAllFuturePrograms();
        return ResponseEntity.ok(FutureProgramList);
    }
    @GetMapping("/getFutureProgram/{id}")
    public ResponseEntity<FutureProgram> getFutureProgram(@PathVariable Long id){
        FutureProgram futureProgram = programService.getFutureProgram(id);
        return ResponseEntity.ok(futureProgram);
    }
    @PutMapping("/updateFutureProgram/{id}")
    public ResponseEntity<String> updateFutureProgram(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String link,
            @RequestParam("photo") List<MultipartFile> photo) throws IOException {

        programService.updatedFutureProgram(id, title, description, link, photo);
        return ResponseEntity.ok("Future Program Updated Successfully_______id : " +id);

    }
    @DeleteMapping("/deleteFutureProgram/{id}")
    public ResponseEntity<String> deleteFutureProgram(@PathVariable Long id){
        programService.deleteFutureProgram(id);
        return ResponseEntity.ok("Future Program  Deleted Successfully_______id : " +id);
    }

}
