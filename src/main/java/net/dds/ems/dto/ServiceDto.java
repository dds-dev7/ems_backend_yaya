package net.dds.ems.dto;

import java.time.LocalDateTime;

public record ServiceDto(
        Integer id,
        String nom,
        String AdminNom
) {}
