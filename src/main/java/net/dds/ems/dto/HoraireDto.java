package net.dds.ems.dto;

import net.dds.ems.entity.Revendeur;

import java.time.LocalTime;
import java.util.Date;

public record HoraireDto(
        Integer id,
        LocalTime dateDebut,
        LocalTime dateFin,
        String jour,
        String revendeur,
        String auteur
) {}
