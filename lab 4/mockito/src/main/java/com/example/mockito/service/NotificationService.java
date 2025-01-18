package com.example.mockito.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public boolean sendNotification(String notification) {
        boolean result = false;
        try {
            System.out.println(notification);
            result = true;
        } catch (Exception e) {
            System.out.println("Exception while sending notification");
        }
        return result;
    }
}