package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.NotificationDto;
import net.dds.ems.dtoMapper.NotificationDtoMapper;
import net.dds.ems.entity.Notification;
import net.dds.ems.repository.NotificationRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationDtoMapper notificationDtoMapper;


    public Notification createNotification(Notification notification) throws Exception {
        try {
            this.notificationRepository.save(notification);

        } catch (Exception ex) {
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
        return this.notificationRepository.save(notification);
    }


    public Notification search(int id) {
        Optional<Notification> optionalNotification = this.notificationRepository.findById(id);
        return optionalNotification.orElseThrow(
                () -> new EntityNotFoundException("Aucune notification n'existe avec cet id")
        );
    }

    public Stream<NotificationDto> showNotification() {
        if (this.notificationRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("No Notification found");
        }
        return this.notificationRepository.findAll().stream().map(notificationDtoMapper);
    }


    public Stream<NotificationDto> showNotificationById(int id) {
        if (this.notificationRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("This Notification cannot be found");
        }
        return this.notificationRepository.findById(id).stream().map(notificationDtoMapper);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Notification updateNotification(int id, Notification notification) throws Exception {
        Notification existingNotification = this.search(id);

        if (notification.getMessage() != null) {
            existingNotification.setMessage(notification.getMessage());
        }
        if (notification.getEtat() != null) {
            existingNotification.setEtat(notification.getEtat());
        }
        try {
            this.notificationRepository.save(existingNotification);
        } catch (Exception ex) {
            throw new BadRequestException("bad syntax for updating notification");
        }
        return this.notificationRepository.save(existingNotification);
    }

    public void deleteNotification(int id) {
        if (!this.notificationRepository.findById(id).isPresent())
            throw new EntityNotFoundException("Aucune notification n'existe avec cet id");
        this.notificationRepository.deleteById(id);
    }
}
