package net.dds.ems.dto;

import net.dds.ems.entity.ServiceRevendeur;

import java.time.LocalDateTime;

public record PaiementCommissionDto(
        Integer id,
        String statut,
        Double montant,
        ServiceRevendeurDto serviceRevendeur,
        String admin,
        LocalDateTime date
) {}
