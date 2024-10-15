package net.dds.ems.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record RevendeurDto(
        Integer id,
        String nom,
        String numero,
        Integer numeroIdentifiant,
        Boolean statut,
        RoleDto role,
        String quartier,
        String Ville,
        String numeroDePiece,
        String secteurActivite,
        String typeDePiece,
        Integer telephoneAirtel,
        Integer telephoneMoov,
        Integer telephoneFlash,
        Boolean crediterCaisse,
        Boolean vendreDirectement,
        LocalDate dateNaissance,
        RecouvreurDto recouvreur,
        LocalDateTime dateCreation
) {}
