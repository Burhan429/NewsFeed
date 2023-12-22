package com.project.NewsFeed.model;

import lombok.Data;


import java.util.Calendar;

@Data
public class EventResponse {
    private String eventId;
    private String eventTitle;
    private String eventLink;
    private String eventDescription;
    private String eventImage;
    private Calendar createdDate;
    private Calendar updateDate;

}
