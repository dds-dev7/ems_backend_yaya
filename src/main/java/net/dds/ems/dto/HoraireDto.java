package net.dds.ems.dto;

import net.dds.ems.entity.Revendeur;

import java.time.LocalTime;
import java.util.Date;

public record HoraireDto(
        Integer id,
        LocalTime heureDebut,
        LocalTime heureFin,
        String jour,
        RevendeurDto revendeur,
        String auteur
) {}
