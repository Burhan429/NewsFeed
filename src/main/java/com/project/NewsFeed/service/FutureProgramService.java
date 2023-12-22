package com.project.NewsFeed.service;

import com.project.NewsFeed.entity.FutureProgram;
import com.project.NewsFeed.repository.FutureProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

@Service
public class FutureProgramService {
    @Autowired
    FutureProgramRepository programRepository;
    public FutureProgram createProgram(FutureProgram futureProgram, MultipartFile image) {
        FutureProgram program= new FutureProgram();
        program.setId(UUID.randomUUID());
        program.setDescription(program.getDescription());
        futureProgram.setLink(program.getLink());
        futureProgram.setTitle(program.getTitle());
        futureProgram.setDate(Calendar.getInstance());
        byte[] fileBytes = new byte[0];
        try {
            fileBytes = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Encode the bytes into a Base64 string
        String encodedString = java.util.Base64.getEncoder().encodeToString(fileBytes);

        futureProgram.setImage(encodedString);

        return programRepository.save(futureProgram);

    }



    public FutureProgram getprogramById(UUID id) {
        FutureProgram program = programRepository.findById(id).orElse(null);

        if (program != null) {
            return program;
        } else {
            return null;
        }
    }


    public void updateProgram(UUID id) {
        FutureProgram existingProgram = this.programRepository.findById(id).get();
        FutureProgram program = new FutureProgram();
       existingProgram.setImage(program.getImage());
       programRepository.save(existingProgram);

    }
}
