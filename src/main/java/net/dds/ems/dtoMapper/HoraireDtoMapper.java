package net.dds.ems.dtoMapper;

import net.dds.ems.dto.HoraireDto;
import net.dds.ems.entity.Horaire;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class HoraireDtoMapper implements Function<Horaire, HoraireDto>{

    @Override
    public HoraireDto apply(Horaire horaire) {
        return new HoraireDto(
                horaire.getId(),
                horaire.getDateDebut(),
                horaire.getDateFin(),
                horaire.getJour(),
                horaire.getRevendeur().getNom(),
                horaire.getAuteur().getNom()
                );
    }
}
