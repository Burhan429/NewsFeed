package com.project.NewsFeed.model;

import lombok.Data;
import java.util.Calendar;

@Data
public class EventResponse {
    private String id;

    private String title;
    private String link;
    private String description;
    private String image;

    private Calendar createdDate;
    private Calendar updateDate;

}
