package com.project.NewsFeed.model;


import lombok.Data;
import java.util.Calendar;

@Data

public class EventRequest {
    private Long id;
    private String title;
    private String link;
    private String description;
    private String photoPath;
    private Calendar date;




}
