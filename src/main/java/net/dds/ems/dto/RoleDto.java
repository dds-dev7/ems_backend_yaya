package net.dds.ems.dto;

import java.time.LocalDateTime;

public record RoleDto(
        int id,
        String nom,
        String droit
) {}
