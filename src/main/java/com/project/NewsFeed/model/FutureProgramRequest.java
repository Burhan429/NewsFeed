package com.project.NewsFeed.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.UUID;
@Data
public class FutureProgramRequest {
    private UUID id ;
    private String title;
    private String link;
    private String description;
    private String image;
    @Temporal(TemporalType.DATE)
    private Calendar date;
}
