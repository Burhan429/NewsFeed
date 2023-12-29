package com.project.NewsFeed.repository;

import com.project.NewsFeed.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

//    @Query("SELECT e.photoPath FROM Event e WHERE e.id = :eventId")
//    String findPhotoPathsById(Long eventId);
}
