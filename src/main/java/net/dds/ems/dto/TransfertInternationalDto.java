package net.dds.ems.dto;

import net.dds.ems.entity.CompteInternationale;

import java.time.LocalDateTime;

public record TransfertInternationalDto(
        Integer id,
        CompteInternationaleDto compte,
        String destinateur,
        RevendeurDto expediteur,
        Double montant,
        Double frais,
        LocalDateTime date
) {}
