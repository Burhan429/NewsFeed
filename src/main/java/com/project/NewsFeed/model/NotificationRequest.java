package com.project.NewsFeed.model;

import lombok.Data;
import java.util.Calendar;

@Data
public class NotificationRequest {
    private String id;
    private String title;
    private String link;
    private String description;
    private Calendar date;
    private Calendar updateDate;
}