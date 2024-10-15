package net.dds.ems.dto;

import java.time.LocalDateTime;

public record RoleDto(
        Integer id,
        String nom,
        String droit,
        String auteur
) {}
