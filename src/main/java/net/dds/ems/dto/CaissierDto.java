package net.dds.ems.dto;

import java.time.LocalDateTime;
import java.util.Date;

public record CaissierDto(
        Integer id,
        String nom,
        String numero,
        Integer numeroIdentifiant,
        Boolean statut,
        String role,
        String quartier,
        String Ville,
        LocalDateTime dateCreation
) {}
