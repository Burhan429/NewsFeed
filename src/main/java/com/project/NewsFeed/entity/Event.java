package com.project.NewsFeed.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String link;
    private String description;
    @ElementCollection
    private List<String> photoPath;
    @Temporal(TemporalType.DATE)
    private Calendar date;


}