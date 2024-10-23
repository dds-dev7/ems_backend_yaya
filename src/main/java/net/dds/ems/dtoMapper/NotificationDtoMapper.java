package net.dds.ems.dtoMapper;

import net.dds.ems.dto.NotificationDto;
import net.dds.ems.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class NotificationDtoMapper implements Function<Notification, NotificationDto>{

    @Override
    public NotificationDto apply(Notification notification) {
        return new NotificationDto(
                notification.getId(),
                notification.getMessage(),
                notification.getType(),
                notification.getEtat());
    }

    public Notification toEntity(NotificationDto notificationDTO) {
        Notification notification = new Notification();
        notification.setId(notificationDTO.id());
        notification.setMessage(notificationDTO.message());
        notification.setMessage(notificationDTO.type());
        notification.setEtat(notificationDTO.etat());
        return notification;
    }
}
