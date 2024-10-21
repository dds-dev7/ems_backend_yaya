package net.dds.ems.dto;

import java.time.LocalDateTime;
import java.util.Date;

public record RecouvreurDto(
        Integer id,
        String nom,
        String numero,
        String motDePasse,
        Integer numeroIdentifiant,
        Boolean statut,
        RoleDto role,
        String quartier,
        String ville,
        Date dateCreation
) {}
