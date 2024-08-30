package net.dds.ems.dto;

import java.time.LocalDateTime;

public record RechargeDto(
        Integer id,
        String type,
        Double montant,
        LocalDateTime date,
        String statut,
        String service,
        String admin,
        String assignerA,
        String acteur
) {}
