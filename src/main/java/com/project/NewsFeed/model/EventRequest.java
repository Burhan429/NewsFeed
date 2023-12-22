package com.project.NewsFeed.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.Lob;
import java.util.Base64;
import java.util.Calendar;

@Data
public class EventRequest {
    private String eventId;
    private String eventTitle;
    private String eventLink;
    private String eventDescription;
    private MultipartFile eventImage;
    private Calendar createdDate;
    private Calendar updateDate;




}
