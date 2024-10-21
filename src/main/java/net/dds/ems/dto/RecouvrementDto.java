package net.dds.ems.dto;

import java.time.LocalDateTime;

public record RecouvrementDto(
        Integer id,
        Double montant,
        ServiceDto service,
        RecouvreurDto agentRecouvreur,
        RevendeurDto agentRevendeur,
        LocalDateTime date
) {}
