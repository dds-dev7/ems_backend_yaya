package net.dds.ems.dto;

import net.dds.ems.entity.Role;

public record AdminDto(
        String nom,
        String numero,
        Boolean statut,
        String role
) {}
