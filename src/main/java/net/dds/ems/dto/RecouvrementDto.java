package net.dds.ems.dto;

import java.time.LocalDateTime;

public record RecouvrementDto(
        Integer id,
        Double montant,
        String service,
        RecouvreurDto recouvreur,
        RevendeurDto revendeur,
        LocalDateTime date
) {}
