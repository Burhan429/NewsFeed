package com.project.NewsFeed.service;

import com.project.NewsFeed.entity.FutureProgram;
import com.project.NewsFeed.repository.FutureProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

@Service
public class FutureProgramService {
    @Autowired
    FutureProgramRepository programRepository;
public void addProgram(String title, String description, MultipartFile photo, String link) throws IOException {
    FutureProgram program = new FutureProgram();
    program.setTitle(title);
    program.setDescription(description);
    program.setLink(link);
    program.setDate(Calendar.getInstance());

    // Convert photo to Base64
    byte[] imageData = photo.getBytes();
    String encodedString = Base64.getEncoder().encodeToString(imageData);
    program.setPhoto(encodedString);
    programRepository.save(program);
}
    public FutureProgram getProgramById(long id) {
        FutureProgram program = programRepository.findById(id).orElse(null);

        if (program != null && program.getPhoto() != null) {
            String base64Photo = program.getPhoto();

            try {
                byte[] photoBytes = Base64.getDecoder().decode(base64Photo);
                program.setDecodedPhoto(photoBytes);
                return program;
            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        return null;
    }

    public
    List<FutureProgram> getAllPrograms() {
        return programRepository.findAll();
    }

    public void updateById(long id ,String title, String description, MultipartFile photo, String link) throws IOException {
        // Retrieve the existing program by ID
        FutureProgram existingProgram = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found with id: " + id));

        // Update the program with the new values
        existingProgram.setTitle(title);
        existingProgram.setDescription(description);
        existingProgram.setLink(link);
        existingProgram.setDate(Calendar.getInstance());

        // Convert and set the new photo if provided
        if (photo != null && !photo.isEmpty()) {
            byte[] imageData = photo.getBytes();
            String encodedString = Base64.getEncoder().encodeToString(imageData);
            existingProgram.setPhoto(encodedString);
        }

        // Save the updated program back to the repository
        programRepository.save(existingProgram);
    }


    public void deleteById(Long id) {
        programRepository.deleteById(id);
    }





}
