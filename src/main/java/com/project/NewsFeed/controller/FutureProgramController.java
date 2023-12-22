package com.project.NewsFeed.controller;

import com.project.NewsFeed.entity.Event;
import com.project.NewsFeed.entity.FutureProgram;
import com.project.NewsFeed.model.EventRequest;
import com.project.NewsFeed.model.FutureProgramRequest;
import com.project.NewsFeed.service.FutureProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.UUID;

@RestController
public class FutureProgramController {
    @Autowired
    FutureProgramService programService;

    public static String uploadDirectory =
            System.getProperty("user.dir") + "src/main/webapp/images";
    @PostMapping("/createProgram")
    public FutureProgram createProgram(
            @RequestParam ("title") String title,
            @RequestParam ("link")String link ,
            @RequestParam ("description") String description,
            @RequestParam ("image") MultipartFile image) throws IOException

    {
        //String originalFileName = image.getOriginalFilename();
        //Path fileNameAndPath = Paths.get(uploadDirectory , originalFileName);
        //Files.write(fileNameAndPath , image.getBytes());

        FutureProgram futureProgram = new FutureProgram();


        return programService.createProgram(futureProgram ,image);
    }

    @GetMapping("/getProgramById/{id}")
    public FutureProgram getProgramById(@PathVariable UUID id) {
        return programService.getprogramById(id);

    }
    @PutMapping("/update{id}")
    public String updateProgram (@PathVariable UUID id){
                programService.updateProgram(id);
                return "Updated";
    }
}
