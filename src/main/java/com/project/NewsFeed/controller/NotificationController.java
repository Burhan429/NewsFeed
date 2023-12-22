package com.project.NewsFeed.controller;

import com.project.NewsFeed.entity.Notification;
import com.project.NewsFeed.model.NotificationRequest;
import com.project.NewsFeed.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @PostMapping("/create")
    public String createNotify(@RequestBody NotificationRequest notificationRequest){
        notificationRequest.setId(UUID.randomUUID().toString());
        return notificationService.createNotify(notificationRequest);
    }
    @GetMapping("/getById/{id}")
    public Notification getNotifyById(@PathVariable String id){
        return notificationService.getNotifyById(id);
    }
    @GetMapping("/getAllNotify")
    public List<Notification> getAllNotify(){
        return notificationService.getAllNotify();
    }
    @PutMapping("/update")
    public String updateNotify(@RequestBody NotificationRequest notificationRequest){
        return notificationService.updateNotify(notificationRequest);
    }
    @DeleteMapping("/deleteById/{id}")
    public String deleteNotify(@PathVariable String id){
         return notificationService.deleteNotify(id);
    }

}
