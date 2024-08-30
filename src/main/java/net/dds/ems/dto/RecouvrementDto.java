package net.dds.ems.dto;

import java.time.LocalDateTime;

public record RecouvrementDto(
        Integer id,
        Double montant,
        String service,
        String recouvreur,
        String revendeur,
        LocalDateTime date
) {}
