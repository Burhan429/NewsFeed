package com.project.NewsFeed.service;

import com.project.NewsFeed.entity.FutureProgram;
import com.project.NewsFeed.repository.FutureProgramRepository;
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
public class FutureProgramService {
    @Autowired
    FutureProgramRepository programRepository;
    public void createFutureProgram(String title, String description, List<MultipartFile> photos, String link) throws IOException {
        FutureProgram futureProgram = new FutureProgram();
        futureProgram.setTitle(title);
        futureProgram.setDescription(description);
        futureProgram.setLink(link);
        futureProgram.setDate(Calendar.getInstance());

        List<String> photoPaths = new ArrayList<>();

        for (MultipartFile photo : photos)
        {
            String photoFileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
            String photoDirectory = "C:\\Projects\\NewsFeed\\src\\main\\resources\\images\\";
            String photoPath = photoDirectory + UUID.randomUUID() + "_" + photoFileName;

            Files.copy(photo.getInputStream(), Paths.get(photoPath), StandardCopyOption.REPLACE_EXISTING);
            photoPaths.add(photoPath);      //Add the photo path to the list
        }
        futureProgram.setPhotoPath(photoPaths);    //Set the list of photo paths in the futureProgram
        programRepository.save(futureProgram);
    }

    public List<FutureProgram> getAllFuturePrograms() {
        return programRepository.findAll();
    }

    public FutureProgram getFutureProgram(Long id) {
        return programRepository.findById(id).orElse(null);
    }


    public void updatedFutureProgram(Long id, String title, String description, String link, List<MultipartFile> photos) throws IOException {
        FutureProgram existingFutureProgram = programRepository.findById(id).get();
        existingFutureProgram.setTitle(title);
        existingFutureProgram.setDescription(description);
        existingFutureProgram.setLink(link);
        List<String> photoPaths = new ArrayList<>();

        for (MultipartFile photo : photos) {

            String photoFileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
            String photoDirectory = "C:\\Projects\\NewsFeed\\src\\main\\resources\\images\\";
            String photoPath = photoDirectory + UUID.randomUUID() + "_" + photoFileName;

            Files.copy(photo.getInputStream(), Paths.get(photoPath), StandardCopyOption.REPLACE_EXISTING);
            photoPaths.add(photoPath);
        }
        existingFutureProgram.setPhotoPath(photoPaths);
        programRepository.save(existingFutureProgram);
    }

    public void deleteFutureProgram(Long id) {
        programRepository.deleteById(id);
    }

}
