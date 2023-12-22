package com.project.NewsFeed.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String eventId;
    private String eventTitle;
    private String eventLink;
    private String eventDescription;
    private String eventImage;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updateDate;

}
