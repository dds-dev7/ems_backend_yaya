package net.dds.ems.dto;

import java.time.LocalDateTime;

public record ServiceRevendeurDto(
        Integer id,
        Double soldeAutorise,
        String service,
        String revendeur,
        Double montantCaisse
) {}
