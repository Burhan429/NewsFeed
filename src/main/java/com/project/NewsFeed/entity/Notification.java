package com.project.NewsFeed.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    private String id;
    private String title;
    private String link;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar date;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updateDate;

}
