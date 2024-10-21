package net.dds.ems.dto;

import net.dds.ems.entity.Role;

public record AdminDto(
        int id,
        String nom,
        String numero,
        Boolean statut,
        String motDePasse,
        RoleDto role
) {}
