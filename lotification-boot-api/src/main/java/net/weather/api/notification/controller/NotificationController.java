package net.weather.api.notification.controller;


import lombok.RequiredArgsConstructor;
import net.weather.api.notification.controller.request.NotificationRequest;
import net.weather.api.notification.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/notification")
    public void sendNotification(@RequestBody NotificationRequest request){
        notificationService.sendNotification(request.userId());
    }
}
