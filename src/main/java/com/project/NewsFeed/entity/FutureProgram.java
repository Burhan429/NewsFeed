package com.project.NewsFeed.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FutureProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String link;
    private String description;
    @Lob
    private String photo;
    @Temporal(TemporalType.DATE)
    private Calendar date;


    public void setDecodedPhoto(byte[] photoBytes) {
        getPhoto();
    }
}
