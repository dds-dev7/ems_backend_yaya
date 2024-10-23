package net.dds.ems.dtoMapper;

import net.dds.ems.dto.HoraireDto;
import net.dds.ems.entity.Horaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class HoraireDtoMapper implements Function<Horaire, HoraireDto>{


    @Autowired
    private RevendeurDtoMapper revendeurDtoMapper;

    @Override
    public HoraireDto apply(Horaire horaire) {
        return new HoraireDto(
                horaire.getId(),
                horaire.getHeureDebut(),
                horaire.getHeureFin(),
                horaire.getJour(),
                revendeurDtoMapper.apply(horaire.getRevendeur()),
                horaire.getAuteur().getNom()
                );
    }

    public Horaire toEntity(HoraireDto horaireDTO) {
        Horaire horaire = new Horaire();
        horaire.setId(horaireDTO.id());
        horaire.setHeureDebut(horaireDTO.heureFin());
        horaire.setHeureFin(horaireDTO.heureFin());
        horaire.setJour(horaireDTO.jour());
        horaire.setRevendeur(revendeurDtoMapper.toEntity(horaireDTO.revendeur()));
        return horaire;
    }
}
