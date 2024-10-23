package net.dds.ems.service;

import jakarta.persistence.EntityNotFoundException;
import net.dds.ems.dto.NotificationDto;
import net.dds.ems.dtoMapper.NotificationDtoMapper;
import net.dds.ems.entity.Notification;
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


    public NotificationDto createNotification(NotificationDto notificationDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (notificationDto.message() == null) {
            throw new BadRequestException("Le message est obligatoire et ne doit pas etre vide");
        }
        if (notificationDto.type() == null) {
            throw new BadRequestException("Le champ type est obligatoire et ne doit pas etre vide");
        }
        if (notificationDto.etat() == null) {
            throw new BadRequestException("le champ etat est obligatoire et ne doit pas etre vide");
        }

        Notification notification = notificationDtoMapper.toEntity(notificationDto);

        try {
            Notification savedNotification = this.notificationRepository.save(notification);
            return notificationDtoMapper.apply(savedNotification);
        } catch (Exception ex) {
            throw new BadRequestException("exception during creating process, Check your syntax!");
        }
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


    public NotificationDto showNotificationById(int id) {
        return notificationDtoMapper.apply(search(id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    public NotificationDto updateNotification(NotificationDto notificationDto) throws Exception {
        // Vérification des champs obligatoires dans le DTO
        if (notificationDto.id() == null) {
            throw new BadRequestException("L'id est obligatoire et ne doit pas etre vide");
        }
        if (notificationDto.message() == null) {
            throw new BadRequestException("Le message est obligatoire et ne doit pas etre vide");
        }
        if (notificationDto.type() == null) {
            throw new BadRequestException("Le champ type est obligatoire et ne doit pas etre vide");
        }
        if (notificationDto.etat() == null) {
            throw new BadRequestException("le champ etat est obligatoire et ne doit pas etre vide");
        }

        Notification notification = notificationDtoMapper.toEntity(notificationDto);

        try {
            Notification savedNotification = this.notificationRepository.save(notification);
            return notificationDtoMapper.apply(savedNotification);
        } catch (Exception ex) {
            throw new BadRequestException("bad syntax for updating notification");
        }
    }

    public void deleteNotification(int id) {
        if (!this.notificationRepository.findById(id).isPresent())
            throw new EntityNotFoundException("Aucune notification n'existe avec cet id");
        this.notificationRepository.deleteById(id);
    }
}
