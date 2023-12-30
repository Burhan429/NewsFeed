package com.project.NewsFeed.controller;

import com.project.NewsFeed.entity.Notification;
import com.project.NewsFeed.model.NotificationRequest;
import com.project.NewsFeed.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/Notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @PostMapping("/createNotify")
    public ResponseEntity<String> createNotify(@RequestBody NotificationRequest notificationRequest){
        notificationRequest.setId(UUID.randomUUID().toString());
         notificationService.createNotify(notificationRequest);
         return ResponseEntity.ok("Notification Saved Successfully");
    }
    @GetMapping("/getNotify/{id}")
    public ResponseEntity<Notification> getNotify(@PathVariable String id){
        Notification getNotify =notificationService.getNotify(id);
        return ResponseEntity.ok(getNotify);
    }
    @GetMapping("/listOfNotify")
    public ResponseEntity<List<Notification>> getAllNotify(){
        List<Notification> getAllNotify = notificationService.getAllNotify();
        return ResponseEntity.ok(getAllNotify);
    }
    @PutMapping("/updateNotify")
    public ResponseEntity<String> updateNotify(@RequestBody NotificationRequest notificationRequest){
         notificationService.updateNotify(notificationRequest);
         return ResponseEntity.ok("Notification Updated Successfully________id : " +notificationRequest.getId());
    }
    @DeleteMapping("/deleteNotify/{id}")
    public ResponseEntity<String> deleteNotify(@PathVariable String id){
         notificationService.deleteNotify(id);
         return ResponseEntity.ok("Notification Deleted Successfully________id : " +id);
    }

}
