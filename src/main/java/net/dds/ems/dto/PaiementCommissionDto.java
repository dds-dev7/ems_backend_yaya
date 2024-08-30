package net.dds.ems.dto;

import java.time.LocalDateTime;

public record PaiementCommissionDto(
        Integer id,
        String statut,
        Double montant,
        Integer serviceRevendeur,
        String admin,
        LocalDateTime date
) {}
