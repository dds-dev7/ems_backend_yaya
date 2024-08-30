package net.dds.ems.dto;

import java.time.LocalDateTime;

public record NotificationDto(
        Integer id,
        String message,
        String etat
) {}
