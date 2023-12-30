package com.project.NewsFeed.service;

import com.project.NewsFeed.entity.Notification;
import com.project.NewsFeed.model.NotificationRequest;
import com.project.NewsFeed.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotify(NotificationRequest notificationRequest) {
        Notification notification = new Notification();
        notification.setId(notificationRequest.getId());
        notification.setTitle(notificationRequest.getTitle());
        notification.setLink(notificationRequest.getLink());
        notification.setDescription(notificationRequest.getDescription());
        notification.setDate(Calendar.getInstance());
         notificationRepository.save(notification);
    }


    public Notification getNotify(String id) {
        return notificationRepository.findById(id).get();
    }

    public List<Notification> getAllNotify() {
        return notificationRepository.findAll();
    }

    public void updateNotify(NotificationRequest notificationRequest) {
        Notification existingNotification = notificationRepository.findById(notificationRequest.getId()).get();
        existingNotification.setId(notificationRequest.getId());
        existingNotification.setTitle(notificationRequest.getTitle());
        existingNotification.setDescription(notificationRequest.getDescription());
        existingNotification.setLink(notificationRequest.getLink());
         notificationRepository.save(existingNotification);

    }

    public void deleteNotify(String id) {
         notificationRepository.deleteById(id);

    }
}
