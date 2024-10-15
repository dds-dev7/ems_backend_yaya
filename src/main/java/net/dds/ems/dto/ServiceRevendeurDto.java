package net.dds.ems.dto;

import java.time.LocalDateTime;

public record ServiceRevendeurDto(
        Integer id,
        Double soldeAutorise,
        ServiceDto service,
        RevendeurDto revendeur,
        Double montantCaisse
) {}
