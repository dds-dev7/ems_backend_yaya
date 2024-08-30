package net.dds.ems.dto;

import java.time.LocalDateTime;

public record TransfertInternationalDto(
        Integer id,
        String compte,
        String destinateur,
        String expediteur,
        Double montant,
        Double frais,
        LocalDateTime date
) {}
