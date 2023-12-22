package com.project.NewsFeed.service;

import com.project.NewsFeed.entity.Notification;
import com.project.NewsFeed.model.NotificationRequest;
import com.project.NewsFeed.model.NotificationResponse;
import com.project.NewsFeed.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public String createNotify(NotificationRequest notificationRequest) {
        Notification notification = new Notification();
        notification.setId(notificationRequest.getId());
        notification.setTitle(notificationRequest.getTitle());
        notification.setLink(notificationRequest.getLink());
        notification.setDescription(notificationRequest.getDescription());
        notification.setDate(Calendar.getInstance());
         notificationRepository.save(notification);
         return "Notification Saved";
    }


    public Notification getNotifyById(String id) {
        return notificationRepository.findById(id).get();
    }

    public List<Notification> getAllNotify() {
        return notificationRepository.findAll();
    }

    public String updateNotify(NotificationRequest notificationRequest) {
        Notification existingNotification = notificationRepository.findById(notificationRequest.getId()).get();
        existingNotification.setId(notificationRequest.getId());
        existingNotification.setTitle(notificationRequest.getTitle());
        existingNotification.setDescription(notificationRequest.getDescription());
        existingNotification.setLink(notificationRequest.getLink());
        existingNotification.setUpdateDate(Calendar.getInstance());
         notificationRepository.save(existingNotification);
         return "Notification Updated Successfully  ID: "+notificationRequest.getId();

    }

    public String deleteNotify(String id) {
         notificationRepository.deleteById(id);
         return "Notification Deleted  ID: "+id;
    }
}
