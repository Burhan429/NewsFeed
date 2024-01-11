package com.project.NewsFeed.controller;

import com.project.NewsFeed.entity.FutureProgram;
import com.project.NewsFeed.repository.FutureProgramRepository;
import com.project.NewsFeed.service.FutureProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FutureProgramController {
    @Autowired
    FutureProgramService programService;
    @Autowired
    FutureProgramRepository programRepository;

@PostMapping("/post")
public ResponseEntity<String> addProgram(@RequestParam("title") String title,
                                              @RequestParam("link") String link,
                                              @RequestParam("description") String description,
                                              @RequestParam("photo") MultipartFile photo) throws IOException {
    programService.addProgram(title, description, photo,link);
    return ResponseEntity.ok("Newsfeed item added successfully");
}

    @GetMapping("/getProgramById/{id}")
    public FutureProgram getProgramById(@PathVariable long id) {
        return programService.getProgramById(id);

    }
    @GetMapping("/get-all")
    public ResponseEntity<List <FutureProgram> > getAllPrograms() {
        List<FutureProgram> programs = programService.getAllPrograms();
        return new ResponseEntity<>(programs, HttpStatus.OK);
    }

    @PutMapping("/updateProgram/{id}")
    public ResponseEntity<String> updateProgram(
            @RequestParam long id ,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam MultipartFile photo,
            @RequestParam String link
    ) {
        try {
            programService.updateById(id , title, description, photo, link);
            return new ResponseEntity<>("Program updated successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error updating program: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteProgram(@PathVariable Long id) {
        try {
            programService.deleteById(id);
            return new ResponseEntity<>("Program deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting program: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
