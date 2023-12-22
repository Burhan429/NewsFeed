package com.project.NewsFeed.repository;

import com.project.NewsFeed.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;

@Repository
public interface EventRepository extends JpaRepository<Event,String> {


}
