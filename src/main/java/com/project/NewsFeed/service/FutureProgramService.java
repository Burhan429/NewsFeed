package com.project.NewsFeed.service;

import com.project.NewsFeed.entity.Event;
import com.project.NewsFeed.entity.FutureProgram;
import com.project.NewsFeed.repository.FutureProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

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

    // Save the photo to a specific directory
    String photoFileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
    String photoDirectory = "C:\\Project\\NewsFeed\\src\\main\\resources\\images";
    String photoPath = photoDirectory + UUID.randomUUID() + "_" + photoFileName;

    Files.copy(photo.getInputStream(), Paths.get(photoPath), StandardCopyOption.REPLACE_EXISTING);

    program.setPhotoPath(photoPath);
    programRepository.save(program);
}
    public Resource getPhotoAsResource(Long id) throws IOException {
        FutureProgram  futureProgram = programRepository.findById(id).orElse(null);
        if (futureProgram != null) {
            Path photoPath = Paths.get(futureProgram.getPhotoPath());
            Resource resource = new ByteArrayResource(Files.readAllBytes(photoPath));
            return resource;
        }
        return null;
    }
    public FutureProgram getProgramById(Long id) {
        return programRepository.findById(id).orElse(null);
    }

    public List<Map<String, Object>> getAllFutureProgramsWithPhotoUrls() {
        List<FutureProgram> allPrograms = programRepository.findAll();

        if (!allPrograms.isEmpty()) {
            List<Map<String, Object>> responseList = new ArrayList<>();

            for (FutureProgram futureProgram : allPrograms) {
                String photoUrl = "/downloadProgramPhoto/image/" + futureProgram.getId(); // URL to download the photo

                Map<String, Object> programMap = new HashMap<>();
                programMap.put("photoUrl", photoUrl);
                programMap.put("id", futureProgram.getId());
                programMap.put("title", futureProgram.getTitle());
                programMap.put("link", futureProgram.getLink());
                programMap.put("description", futureProgram.getDescription());
                programMap.put("photoPath", futureProgram.getPhotoPath());
                programMap.put("date", futureProgram.getDate());

                responseList.add(programMap);
            }

            return responseList;
        } else {
            return Collections.emptyList();
        }
    }

    public void updateById(Long id, String title, String description, MultipartFile photo, String link) throws IOException {
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
            // Save the new photo to the specified directory
            String photoFileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
            String photoDirectory = "C:\\Project\\NewsFeed\\src\\main\\resources\\images";
            String photoPath = photoDirectory + UUID.randomUUID() + "_" + photoFileName;

            Files.copy(photo.getInputStream(), Paths.get(photoPath), StandardCopyOption.REPLACE_EXISTING);

            // Set the photo path in the program
            existingProgram.setPhotoPath(photoPath);
        }

        // Save the updated program back to the repository
        programRepository.save(existingProgram);
    }


//    public void deleteById(Long id) {
//        programRepository.deleteById(id);
//    }





}
