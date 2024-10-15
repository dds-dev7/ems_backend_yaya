package net.dds.ems.dto;

import java.time.LocalDateTime;

public record TransactionRecouvreurDto(
        Integer id,
        String type,
        Double montant,
        RecouvreurDto effectuerPar,
        CaissierDto assignerA,
        LocalDateTime date
) {}
