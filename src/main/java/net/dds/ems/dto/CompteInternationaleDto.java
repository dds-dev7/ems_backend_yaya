package net.dds.ems.dto;

public record CompteInternationaleDto(
        Integer id,
        String pays,
        String mNC,
        Double commission,
        String adminNom
) {}
