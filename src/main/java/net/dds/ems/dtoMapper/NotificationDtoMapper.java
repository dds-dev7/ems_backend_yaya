package net.dds.ems.dtoMapper;

import net.dds.ems.dto.NotificationDto;
import net.dds.ems.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class NotificationDtoMapper implements Function<Notification, NotificationDto>{

    @Override
    public NotificationDto apply(Notification notification) {
        return new NotificationDto(notification.getId(), notification.getMessage(), notification.getEtat());
    }
}
