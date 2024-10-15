package net.dds.ems.dto;

import net.dds.ems.entity.Role;

import java.time.LocalDateTime;
import java.util.Date;

public record CaissierDto(
        Integer id,
        String nom,
        String numero,
        Integer numeroIdentifiant,
        Boolean statut,
        RoleDto role,
        String quartier,
        String Ville,
        LocalDateTime dateCreation
) {}
