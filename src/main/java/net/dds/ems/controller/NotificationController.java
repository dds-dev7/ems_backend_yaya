package net.dds.ems.controller;

import net.dds.ems.dto.NotificationDto;
import net.dds.ems.entity.Notification;
import net.dds.ems.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/notification")
@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping(path = "/create")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) throws Exception {
        Notification createdNotification = this.notificationService.createNotification(notification);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED)
;    }

    @GetMapping(path = "/read")
    public Stream<NotificationDto> showNotification(){
        return this.notificationService.showNotification();
    }

    @GetMapping(path = "/read/{id}")
    public Stream<NotificationDto> showNotificationById(@PathVariable int id){
        return this.notificationService.showNotificationById(id);
    }

    @PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> updateNotification(@PathVariable int id, @RequestBody Notification notification) throws Exception {
        Notification updatedNotification = this.notificationService.updateNotification(id, notification);
        return new ResponseEntity<>(updatedNotification, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public void deleteNotification(@PathVariable int id){
        this.notificationService.deleteNotification(id);
    }
}
