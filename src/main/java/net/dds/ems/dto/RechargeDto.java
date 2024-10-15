package net.dds.ems.dto;

import java.time.LocalDateTime;

public record RechargeDto(
        Integer id,
        String type,
        Double montant,
        LocalDateTime date,
        String statut,
        ServiceDto service,
        String adminNom,
        RevendeurDto assignerA,
        String acteur
) {}
